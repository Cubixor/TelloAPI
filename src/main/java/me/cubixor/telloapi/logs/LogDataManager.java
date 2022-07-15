package me.cubixor.telloapi.logs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.logpackets.*;
import me.cubixor.telloapi.utils.Utils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogDataManager {

    private static Class<?>[] logPacketClasses;
    private final Drone tello;
    private final StringBuilder raw = new StringBuilder();
    private final List<LogRecord> logRecords = new ArrayList<>();
    private LogRecord currentLogRecord;
    private boolean complete = false;

    public LogDataManager(Drone tello) {
        this.tello = tello;

        loadLogRecordsFromFiles(getFileFromResourceAsStream("logRecords.json"));
        loadLogRecordsFromFiles(getFileFromResourceAsStream("logRecordsUndefined.json"));

        getClassesFromPackage();
    }

    public static Class<?> matchLogPacketClass(int id) {
        for (Class<?> logPacketClass : logPacketClasses) {
            try {
                int classID = (int) logPacketClass.getField("PACKET_ID").get(null);
                if (classID == id) {
                    return logPacketClass;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public void getClassesFromPackage() {
/*
        Set<Class<?>> classes = Utils.findAllClassesUsingClassLoader(
                "me.cubixor.telloapi.logs.logpackets");

        logPacketClasses = new Class<?>[classes.size()];

        int index = 0;
        for (Class<?> clazz : classes) {
            logPacketClasses[index] = clazz;
            index++;
        }
*/

        logPacketClasses = new Class<?>[]{
                OsdDataPacket.class,
                UsonicPakcet.class,
                MvoFeedbackPacket.class,
                ControllerPacket.class,
                AircraftConditionPacket.class,
                SerialApiInputsPacket.class,
                CtrlVertDebug.class,
                CtrlVelVertDebug.class,
                CtrlAccVertDebug.class,
                CtrlHorizDebugPacket.class,
                FlyLimitVelDebugPacket.class,
                CtrlHorizAttiDebugPacket.class,
                CtrlHorizAngVelDebugPacket.class,
                CtrlAllocationDebugPacket.class,
                MotorControlPacket.class,
                BatteryInfoPacket.class,
                ImuAtti0Packet.class,
                ImuEx0Packet.class,
                AttiMini0Packet.class,
                NsDataDebugPacket.class,
                NsDataComponentPacket.class,
                AirCompensateDataPacket.class
        };
    }

    private void loadLogRecordsFromFiles(InputStream jsonFile) {
        try (Reader jsonFileReader = new InputStreamReader(jsonFile)) {
            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<LogRecord>>() {
            }.getType();

            List<LogRecord> recordsFromFile = gson.fromJson(jsonFileReader, type);
            logRecords.addAll(recordsFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogConfigData(byte[] data) {
        if (currentLogRecord == null) {
            logRecords.clear();
        }

        String dataString = toStringTrimmed(data);
        raw.append(dataString).append("\n");

        if (dataString.startsWith("Op.[config]")) {
            String[] lines = dataString.split("\n");

            String name = lines[1].split("\t")[1];
            String id = lines[2].split("\t")[1];

            LogRecord logRecord = new LogRecord(name, Integer.parseInt(id));
            currentLogRecord = logRecord;
            logRecords.add(logRecord);
        } else {
            LogField logField = createLogRecordField(dataString);
            currentLogRecord.getFields().add(logField);
        }
    }

    private LogField createLogRecordField(String data) {
        String trimmed = data.substring(3, data.length() - 2);
        String[] split = trimmed.split("\t");
        String dataType = split[0];
        String name = split[1];

        return new LogField(name, dataType);
    }

    private String toStringTrimmed(byte[] data) {
        String string = new String(ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).array(), StandardCharsets.UTF_8);
        return string.substring(0, string.length() - 1);
    }

    public void setComplete() {
        complete = true;
        System.out.println("LOG CONFIG COMPLETE");

        for (LogRecord logRecord : logRecords) {
            logRecord.calculateSize();
        }

        saveRecordsToFile();
    }

    private void saveRecordsToFile() {
        System.out.println(logRecords);

        Gson gson = new Gson();

        try (FileWriter jsonFile = new FileWriter("logRecords.json");
             FileWriter rawFile = new FileWriter("logRecords.txt")) {

            gson.toJson(logRecords, jsonFile);
            jsonFile.flush();

            rawFile.write(raw.toString());
            rawFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decodeLog(byte[] data) {
        int pos = 0;

        while (pos < data.length) {
            byte magic = data[pos];
            if (magic != 0x55) {
                //System.out.println("LOG DATA PACKET CORRUPT: " + Utils.bytesToHex(data));
                return;
            }
            int len = data[pos + 1] & 0xff;
            byte alwaysZero = data[pos + 2];
            if (alwaysZero != 0) {
                //System.out.println("LOG DATA PACKET CORRUPT: " + Utils.bytesToHex(data));
                return;
            }
            byte crc = data[pos + 3];
            int id = Utils.connectBytes(data[pos + 4], data[pos + 5]);
            int tick = Utils.connectBytes(data[pos + 6], data[pos + 7], data[pos + 8], data[pos + 9]);
            //int crcEnd = Utils.connectBytes(data[pos + len - 2], data[pos + len - 1]);

/*
            int crcExpected = Crc.calcCRC8(data, 3);
            byte[] dataNoCrc = Arrays.copyOfRange(data, pos, pos + len - 2);
            int crcEndExpected = Crc.calcCRC16(dataNoCrc, dataNoCrc.length);
            assert crcExpected == crc : "Invalid packet crc8";
            assert crcEndExpected == crcEnd : "Invalid packet crc16";
*/


            byte xorValue = data[pos + 6];
            byte[] recordData = Arrays.copyOfRange(data, pos + 10, pos + len - 2);
            byte[] dataDecrypted = new byte[recordData.length];

            for (int i = 0; i < recordData.length; i++) {
                dataDecrypted[i] = (byte) (recordData[i] ^ xorValue);
            }
            pos += len;

            //Log config packet id
            if (id == 65533) {
                System.out.println("LOG CONFIG DECODED:    DATA: " + Utils.bytesToHex(dataDecrypted));
                handleLogConfigData(dataDecrypted);
                continue;
            }

            LogRecord logRecord = matchById(id);

            if (logRecord == null) {
                continue;
            }

            Object[] convertedFields = new Object[logRecord.getFields().size()];

            int dataIndex = 0;
            for (LogField logField : logRecord.getFields()) {
                LogDataType logDataType = LogDataType.matchByName(logField.getDataType());
                int size = logDataType.getSize();
                byte[] fieldData = Arrays.copyOfRange(dataDecrypted, dataIndex, dataIndex + size);

                Object object = logDataType.convertData(fieldData);
                convertedFields[logRecord.getFields().indexOf(logField)] = object;

                dataIndex += size;
            }

            Class<?> logPacketClass = LogDataManager.matchLogPacketClass(id);
            if (logPacketClass == null) {
                //System.out.println("Unknown ID: " + id + " | LEN: " + (len - 12) + " | DATA: " + Utils.bytesToHex(dataDecrypted));
                continue;
            }

            try {
                logPacketClass.getConstructor(Drone.class, int.class, Object[].class).newInstance(tello, tick, convertedFields);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public LogRecord matchById(int id) {
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getId() == id) {
                return logRecord;
            }
        }
        return null;
    }
}
