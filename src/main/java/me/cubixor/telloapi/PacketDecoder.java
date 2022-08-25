package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneStatusListener;
import me.cubixor.telloapi.api.FileReceiver;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.logs.LogDataManager;
import me.cubixor.telloapi.photo.File;
import me.cubixor.telloapi.photo.FileChunk;
import me.cubixor.telloapi.photo.FilePiece;
import me.cubixor.telloapi.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;

public class PacketDecoder {

    private final Drone tello;
    LogDataManager logDataManager;

    public PacketDecoder(Drone tello) {
        this.tello = tello;
        this.logDataManager = new LogDataManager(tello);
    }

    public void handlePacket(byte[] data) {
        byte[] trimmed = ByteUtils.trim(data);

        byte prefix = trimmed[0];
        if (prefix == (byte) 0xCC) {
            short messageID = ByteBuffer.wrap(Arrays.copyOfRange(trimmed, 5, 7)).order(ByteOrder.LITTLE_ENDIAN).getShort();
            MessageType messageType = MessageType.getMessageType(messageID);
            byte[] payload = trimmed.length > 11 ? Arrays.copyOfRange(trimmed, 9, trimmed.length - 2) : new byte[0];

            //System.out.println(messageType.toString() + " " + Utils.bytesToHex(payload));

            tello.resetTimeout();
            decodePacket(messageType, payload, trimmed);
        } else if (ByteUtils.startsWith(trimmed, "conn_ack:".getBytes())) {
            tello.onConnect();
        }
    }

    private void decodePacket(MessageType messageType, byte[] payload, byte[] fullData) {
        switch (messageType) {
            case QUERY_SSID: {
                decodeSSIDPacket(payload);
                break;
            }
            case QUERY_PASSWORD: {
                decodePasswordPacket(payload);
                break;
            }
            case QUERY_REGION: {
                decodeRegionPacket(payload);
                break;
            }
            case QUERY_VERSION: {
                decodeVersionPacket(payload);
                break;
            }
            case SET_DATE_TIME: {
                decodeDateTimePacket();
                break;
            }
            case QUERY_ACTIVATION_TIME: {
                decodeActivationTimePacket(payload);
                break;
            }
            case QUERY_LOADER_VERSION: {
                decodeLoaderVersionPacket(payload);
                break;
            }
            case QUERY_BITRATE: {
                decodeBitratePacket(payload);
                break;
            }
            case LIGHT_STRENGTH: {
                decodeLightStrengthPacket(payload);
                break;
            }
            case WIFI_STRENGTH: {
                decodeWifiStrengthPacket(payload);
                break;
            }
            case TAKEOFF: {
                decodeTakeoffPacket(payload);
                break;
            }
            case LAND: {
                decodeLandPacket(payload);
                break;
            }
            case STATUS: {
                decodeStatusPacket(payload);
                break;
            }
            case FILE_SIZE: {
                decodeFileSizePacket(payload);
                break;
            }
            case FILE_DATA: {
                decodeFileDataPacket(payload);
                break;
            }
            case SMART_VIDEO_STATUS: {
                decodeSmartVideoStatus(payload);
                break;
            }
            case LOG_HEADER: {
                decodeLogHeaderPacket(payload);
                break;
            }
            case LOG_DATA: {
                decodeLogDataPacket(payload);
                break;
            }
            case LOG_CONFIG: {
                //decodeLogConfigPacket(payload);
                break;
            }
            case QUERY_HEIGHT_LIMIT: {
                decodeHeightLimitPacket(payload);
                break;
            }
            case QUERY_LOW_BATTERY_THRESHOLD: {
                decodeLowBatteryThresholdPacket(payload);
                break;
            }
            case QUERY_ATTITUDE_LIMIT: {
                decodeAttitudeLimitPacket(payload);
                break;
            }
            default: {
                System.out.println("RECEIVE;   SIZE: " + fullData.length + "   DATA: " + ByteUtils.bytesToHex(fullData));
                break;
            }
        }
    }

    private void decodeSSIDPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 1, payload.length);
        String ssid = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setWifiSSID(ssid);
    }

    private void decodePasswordPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 2, payload.length);
        String password = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setWifiPassword(password);
    }

    private void decodeRegionPacket(byte[] payload) {
        byte[] data = ByteUtils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String region = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setWifiRegion(region);
    }

    private void decodeVersionPacket(byte[] payload) {
        byte[] data = ByteUtils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String version = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setVersion(version);
    }

    private void decodeDateTimePacket() {
        tello.getPacketSender().sendSetDateTimePacket(LocalDateTime.now());
    }

    private void decodeLoaderVersionPacket(byte[] payload) {
        byte[] data = ByteUtils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String version = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setLoaderVersion(version);
    }

    private void decodeActivationTimePacket(byte[] payload) {
        //First byte seems to be always 0
        //After 29 bytes data repeats (no clue why)

        int year = ByteUtils.connectBytes(payload[1], payload[2]);
        int month = ByteUtils.connectBytes(payload[3], payload[4]);
        int day = ByteUtils.connectBytes(payload[5], payload[6]);

        //Not sure about these four
        int hour = ByteUtils.connectBytes(payload[7], payload[8]);
        int minute = ByteUtils.connectBytes(payload[9], payload[10]);
        int second = ByteUtils.connectBytes(payload[11], payload[12]);
        int zero = ByteUtils.connectBytes(payload[13], payload[14]);

        //Serial number seems to be always zero
        String sn = new String(Arrays.copyOfRange(payload, 15, 30), StandardCharsets.UTF_8);

        LocalDateTime activationTime = LocalDateTime.of(year, month, day, hour, minute, second, zero);
        tello.getDroneStateManager().setActivationTime(activationTime);
    }

    //Not working
    //Always getting same two bytes - 00 00
    //After reading the original app's code it looks like this was never working, as the code for receiving bitrate just gets the second byte and does nothing else with it.
    private void decodeBitratePacket(byte[] payload) {
        System.out.println("BITRATE " + ByteUtils.bytesToHex(payload));
    }


    private void decodeLightStrengthPacket(byte[] payload) {
        if (payload.length != 1) {
            return;
        }
        boolean lightOK = payload[0] == 0;

        tello.getDroneStateManager().setLightOK(lightOK);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onLightStrengthPacketReceive(lightOK);
        }
    }

    private void decodeWifiStrengthPacket(byte[] payload) {
        if (payload.length != 2) {
            return;
        }

        int wifiStrength = payload[0];
        int wifiInterference = payload[1];

        tello.getDroneStateManager().setWifiStrength(wifiStrength);
        tello.getDroneStateManager().setWifiInterference(wifiInterference);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onWifiStrengthPacketReceive(wifiStrength, wifiInterference);
        }
    }

    private void decodeTakeoffPacket(byte[] payload) {
        System.out.println("takeoff " + ByteUtils.bytesToHex(payload));
    }

    private void decodeLandPacket(byte[] payload) {
        System.out.println("land " + ByteUtils.bytesToHex(payload));
    }

    private void decodeStatusPacket(byte[] payload) {

        //TODO Status packet comes in different sizes
        if (payload.length != 24) {
            return;
        }

        tello.getDroneStateManager().updateDroneStatus(payload);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onStatusPacketReceive(tello.getDroneStateManager());
        }
    }

    private void decodeFileSizePacket(byte[] payload) {
        int fileSize = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 1, 5));
        int fileID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 5, 7));

        tello.getPendingFiles().put(fileID, new File(fileID, fileSize));
        tello.getPacketSender().sendFileSizePacket();

        System.out.println("FILESIZE  " + tello.getPendingFiles().get(fileID));
    }

    private void decodeFileDataPacket(byte[] payload) {
        int fileID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 0, 2));
        int filePieceID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 2, 6));
        int fileChunkID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 6, 10));
        int fileChunkLength = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 10, 12));
        byte[] data = Arrays.copyOfRange(payload, 12, payload.length);

        File file = tello.getPendingFiles().get(fileID);

        System.out.println("FILE:" + fileID + "  FILESIZE:" + file.getFileSize() + "  FILECURRENTSIZE:" + file.getCurrentSize() + "   FILEPIECE:" + filePieceID + "  FILECHUNK:" + fileChunkID + " CHUNKSIZE:" + fileChunkLength + " ACTUALCHUNKSIZE:" + data.length);


        if (file.isReceived()) {
            return;
        }

        if (!file.getFilePieces().containsKey(filePieceID)) {
            file.getFilePieces().put(filePieceID, new FilePiece(filePieceID));
        }

        FilePiece filePiece = file.getFilePieces().get(filePieceID);

        if (fileChunkLength == data.length) {
            if (filePiece.getFileChunks()[fileChunkID - 8 * filePieceID] == null) {
                filePiece.getFileChunks()[fileChunkID - 8 * filePieceID] = new FileChunk(fileChunkID, fileChunkLength, data);
                file.addCurrentSize(fileChunkLength);
            }
        }


        if (file.getCurrentSize() >= file.getFileSize()) {
            tello.getPacketSender().sendFileDataPacket(true, fileID, filePieceID);
            tello.getPacketSender().sendFileCompletePacket(fileID, file.getFileSize());
            file.setReceived(true);

            System.out.println(file);

            byte[] b = file.toByteArray();

            for (FileReceiver fileReceiver : tello.getFileReceivers()) {
                fileReceiver.onPhotoReceived(b);
            }

            return;
        }

        if (!Arrays.asList(filePiece.getFileChunks()).contains(null)) {
            System.out.println("SEND PIECE DONE   PIECE:" + filePieceID);
            tello.getPacketSender().sendFileDataPacket(false, fileID, filePieceID);
        }

    }

    private void decodeSmartVideoStatus(byte[] payload) {
        System.out.println("SMARTVIDEO " + ByteUtils.bytesToHex(payload));

        if (payload.length == 0) {
            return;
        }

        byte videoModeID = (byte) (payload[0] >> 5);
        VideoInfo.SmartVideoMode videoMode = VideoInfo.SmartVideoMode.getByID(videoModeID);
        boolean running = (payload[0] >> 3 & 1) == 1;

        tello.getVideoInfo().setSmartVideoMode(videoMode);
        tello.getVideoInfo().setSmartVideoRunning(running);

        System.out.println("SMARTVIDEOMODE " + tello.getVideoInfo().getSmartVideoMode());
        System.out.println("SMARTVIDEORUNNING " + tello.getVideoInfo().isSmartVideoRunning());

        for (DroneStatusListener droneStatusListener : tello.getPacketReceivers()) {
            droneStatusListener.onSmartVideoPacketReceive(videoMode, running);
        }
    }

    private void decodeLogHeaderPacket(byte[] payload) {
        System.out.println("LOG HEADER:    SIZE: " + payload.length + "   DATA: " + ByteUtils.bytesToHex(payload));
        short seqId = (short) ByteUtils.connectBytes(payload[0], payload[1]);
        byte[] data = Arrays.copyOfRange(payload, 3, payload.length);

        //LogManager.getInstance().createFile();
        //LogManager.getInstance().writeToFile(data);

        tello.getPacketSender().sendLogHeaderAckPacket(seqId);
    }

    private void decodeLogDataPacket(byte[] payload) {
        //System.out.println("LOG DATA:    SIZE: " + payload.length + "   DATA: " + Utils.bytesToHex(payload));

        byte[] data = Arrays.copyOfRange(payload, 1, payload.length);
        logDataManager.decodeLog(data);

        //LogManager.getInstance().writeToFile(data);
    }

    private void decodeLogConfigPacket(byte[] payload) {
        System.out.println("LOG CONFIG:    SIZE: " + payload.length + "   DATA: " + ByteUtils.bytesToHex(payload));

        byte[] data = Arrays.copyOfRange(payload, 8, payload.length);
        short data1 = ByteBuffer.wrap(Arrays.copyOfRange(payload, 1, 3)).order(ByteOrder.LITTLE_ENDIAN).getShort();
        int data2 = ByteBuffer.wrap(Arrays.copyOfRange(payload, 3, 7)).order(ByteOrder.LITTLE_ENDIAN).getInt();

        boolean done = payload.length == 8 && payload[7] == 0;
        if (!done) {
            logDataManager.decodeLog(data);
        } else {
            logDataManager.setComplete();
        }

        //LogManager.getInstance().writeToFile(data);

        tello.getPacketSender().sendLogConfigAckPacket(data1, data2);
    }

    private void decodeHeightLimitPacket(byte[] payload) {
        tello.getDroneStateManager().setHeightLimit(payload[1]);
    }

    private void decodeLowBatteryThresholdPacket(byte[] payload) {
        tello.getDroneStateManager().setLowBatteryThreshold(payload[1]);
    }

    private void decodeAttitudeLimitPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 1, payload.length);
        float attitude;

        if (data.length == 3 && data[2] == -56) {
            attitude = 25.0f;
        } else {
            attitude = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        }

        tello.getDroneStateManager().setMaxAttitudeAngle(attitude);
    }

}
