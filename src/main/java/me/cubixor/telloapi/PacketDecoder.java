package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneStatus;
import me.cubixor.telloapi.api.listeners.DroneStatusListener;
import me.cubixor.telloapi.api.listeners.FileMonitor;
import me.cubixor.telloapi.api.listeners.FileReceiver;
import me.cubixor.telloapi.api.video.SmartVideoMode;
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
import java.util.logging.Logger;

public class PacketDecoder {

    private final Drone tello;
    private final Logger logger = Logger.getLogger(PacketDecoder.class.getName());

    private final LogDataManager logDataManager;

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

            tello.resetTimeout();
            decodePacket(messageType, payload);
        } else if (ByteUtils.startsWith(trimmed, "conn_ack:".getBytes())) {
            tello.onConnect();
        }
    }

    private void decodePacket(MessageType messageType, byte[] payload) {
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
            case SET_SSID: {
                decodeSetSSIDPacket(payload);
                break;
            }
            case SET_PASSWORD: {
                decodeSetPasswordPacket(payload);
                break;
            }
            case SET_REGION: {
                decodeSetRegionPacket(payload);
                break;
            }
            case QUERY_VERSION: {
                decodeVersionPacket(payload);
                break;
            }
            case SET_DATE_TIME: {
                decodeDateTimePacket(payload);
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
                decodeLogConfigPacket(payload);
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
            case SET_HEIGHT_LIMIT: {
                decodeSetHeightLimitPacket(payload);
                break;
            }
            case SET_ATTITUDE_LIMIT: {
                decodeSetAttitudeLimitPacket(payload);
                break;
            }
            case SET_LOW_BATTERY_THRESHOLD: {
                decodeSetLowBatteryThresholdPacket(payload);
                break;
            }
            default: {
                logger.warning("Unknown packet received! Size: " + payload.length + " Data: " + ByteUtils.bytesToHex(payload));
                break;
            }
        }
    }

    private void decodeSSIDPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 2, payload.length);
        String ssid = new String(data, StandardCharsets.UTF_8);
        tello.getDroneState().setWifiSSID(ssid);

        log(MessageType.QUERY_SSID, ssid);
    }

    private void decodePasswordPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 2, payload.length);
        String password = new String(data, StandardCharsets.UTF_8);
        tello.getDroneState().setWifiPassword(password);

        log(MessageType.QUERY_PASSWORD, password);
    }

    private void decodeRegionPacket(byte[] payload) {
        byte[] data = ByteUtils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String region = new String(data, StandardCharsets.UTF_8);
        tello.getDroneState().setWifiRegion(region);

        log(MessageType.QUERY_REGION, region);
    }


    private void decodeSetSSIDPacket(byte[] payload) {
        log(MessageType.SET_SSID, ByteUtils.bytesToHex(payload));
    }

    private void decodeSetPasswordPacket(byte[] payload) {
        log(MessageType.SET_PASSWORD, ByteUtils.bytesToHex(payload));
    }

    private void decodeSetRegionPacket(byte[] payload) {
        log(MessageType.SET_REGION, ByteUtils.bytesToHex(payload));
    }

    private void decodeVersionPacket(byte[] payload) {
        byte[] data = ByteUtils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String version = new String(data, StandardCharsets.UTF_8);
        tello.getDroneState().setVersion(version);

        log(MessageType.QUERY_VERSION, version);
    }

    private void decodeDateTimePacket(byte[] payload) {
        tello.getPacketSender().sendSetDateTimePacket(LocalDateTime.now());

        log(MessageType.SET_DATE_TIME, ByteUtils.bytesToHex(payload));
    }

    private void decodeLoaderVersionPacket(byte[] payload) {
        byte[] data = ByteUtils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String version = new String(data, StandardCharsets.UTF_8);
        tello.getDroneState().setLoaderVersion(version);

        log(MessageType.QUERY_LOADER_VERSION, version);
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
        tello.getDroneState().setActivationTime(activationTime);

        log(MessageType.QUERY_ACTIVATION_TIME, "Activation time: " + activationTime + " Serial number: " + sn);
    }

    //Not working
    //Always getting same two bytes - 00 00
    //After reading the original app's code it looks like this was never working, as the code for receiving bitrate just gets the second byte and does nothing else with it.
    private void decodeBitratePacket(byte[] payload) {
        log(MessageType.QUERY_BITRATE, ByteUtils.bytesToHex(payload));
    }


    private void decodeLightStrengthPacket(byte[] payload) {
        if (payload.length != 1) {
            return;
        }
        boolean lightOK = payload[0] == 0;

        tello.getDroneState().setLightOK(lightOK);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onLightStrengthPacketReceive(lightOK);
        }

        log(MessageType.LIGHT_STRENGTH, Boolean.toString(lightOK));
    }

    private void decodeWifiStrengthPacket(byte[] payload) {
        if (payload.length != 2) {
            return;
        }

        int wifiStrength = payload[0];
        int wifiInterference = payload[1];

        tello.getDroneState().setWifiStrength(wifiStrength);
        tello.getDroneState().setWifiInterference(wifiInterference);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onWifiStrengthPacketReceive(wifiStrength, wifiInterference);
        }

        log(MessageType.WIFI_STRENGTH, "Strength: " + wifiStrength + " Interference: " + wifiInterference);
    }

    private void decodeTakeoffPacket(byte[] payload) {
        log(MessageType.TAKEOFF, ByteUtils.bytesToHex(payload));
    }

    private void decodeLandPacket(byte[] payload) {
        log(MessageType.LAND, ByteUtils.bytesToHex(payload));
    }

    private void decodeStatusPacket(byte[] payload) {

        //TODO Status packet comes in different sizes
        if (payload.length != 24) {
            return;
        }

        DroneStatus droneStatus = tello.getDroneState().updateDroneStatus(payload);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onStatusPacketReceive(droneStatus);
        }

        log(MessageType.STATUS, droneStatus.toString());
    }

    private void decodeFileSizePacket(byte[] payload) {
        int fileSize = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 1, 5));
        int fileID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 5, 7));

        tello.getPendingFiles().put(fileID, new File(fileID, fileSize));
        tello.getPacketSender().sendFileSizePacket();

        log(MessageType.FILE_SIZE, "File size: " + fileSize + " File ID: " + fileID);
    }

    private void decodeFileDataPacket(byte[] payload) {
        int fileID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 0, 2));
        int filePieceID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 2, 6));
        int fileChunkID = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 6, 10));
        int fileChunkLength = ByteUtils.connectBytes(Arrays.copyOfRange(payload, 10, 12));
        byte[] data = Arrays.copyOfRange(payload, 12, payload.length);

        File file = tello.getPendingFiles().get(fileID);

        log(MessageType.FILE_DATA, "File ID: " + fileID + " File piece ID: " + filePieceID + " File chunk ID :" + fileChunkID + " File chunk length: " + fileChunkLength + " File chunk data: " + ByteUtils.bytesToHex(data));

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
                for (FileMonitor fileMonitor : tello.getFileMonitors()) {
                    fileMonitor.onPhotoSending(file.getPercentageDone());
                }
            }
        }


        if (file.getCurrentSize() >= file.getFileSize()) {
            tello.getPacketSender().sendFileDataPacket(true, fileID, filePieceID);
            tello.getPacketSender().sendFileCompletePacket(fileID, file.getFileSize());
            file.setReceived(true);


            byte[] b = file.toByteArray();

            for (FileReceiver fileReceiver : tello.getFileReceivers()) {
                fileReceiver.onPhotoReceived(b);
            }

            logger.info("Received full photo! " + file);

            return;
        }

        if (!Arrays.asList(filePiece.getFileChunks()).contains(null)) {
            tello.getPacketSender().sendFileDataPacket(false, fileID, filePieceID);
        }

    }

    private void decodeSmartVideoStatus(byte[] payload) {
        if (payload.length == 0) {
            return;
        }

        byte videoModeID = (byte) (payload[0] >> 5);
        SmartVideoMode videoMode = SmartVideoMode.getByID(videoModeID);
        boolean running = (payload[0] >> 3 & 1) == 1;

        tello.getVideoInfo().setSmartVideoMode(videoMode);
        tello.getVideoInfo().setSmartVideoRunning(running);

        for (DroneStatusListener droneStatusListener : tello.getPacketReceivers()) {
            droneStatusListener.onSmartVideoPacketReceive(videoMode, running);
        }

        log(MessageType.SMART_VIDEO_STATUS, "Video mode: " + videoMode + " Is running: " + running);
    }

    private void decodeLogHeaderPacket(byte[] payload) {
        short seqId = (short) ByteUtils.connectBytes(payload[0], payload[1]);
        byte[] data = Arrays.copyOfRange(payload, 3, payload.length);

        //LogManager.getInstance().writeToFile(data);

        tello.getPacketSender().sendLogHeaderAckPacket(seqId);

        log(MessageType.LOG_HEADER, "Sequence ID: " + seqId + " Data: " + ByteUtils.bytesToHex(data));
    }

    private void decodeLogDataPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 1, payload.length);
        try {
            logDataManager.decodeLog(data);
        } catch (Exception e) {
            logger.warning("Error parsing log data packet!");
            e.printStackTrace();
        }

        //LogManager.getInstance().writeToFile(data);
    }

    private void decodeLogConfigPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 8, payload.length);
        short data1 = ByteBuffer.wrap(Arrays.copyOfRange(payload, 1, 3)).order(ByteOrder.LITTLE_ENDIAN).getShort();
        int data2 = ByteBuffer.wrap(Arrays.copyOfRange(payload, 3, 7)).order(ByteOrder.LITTLE_ENDIAN).getInt();

        boolean done = payload.length == 8 && payload[7] == 0;
        if (!done) {
            try {
                logDataManager.decodeLog(data);
            } catch (Exception e) {
                logger.warning("Error parsing log config packet!");
                e.printStackTrace();
            }
        } else {
            logDataManager.setComplete();
        }

        //LogManager.getInstance().writeToFile(data);

        tello.getPacketSender().sendLogConfigAckPacket(data1, data2);
    }

    private void decodeHeightLimitPacket(byte[] payload) {
        tello.getDroneState().setHeightLimit(payload[1]);

        log(MessageType.QUERY_HEIGHT_LIMIT, String.valueOf(payload[1]));
    }

    private void decodeLowBatteryThresholdPacket(byte[] payload) {
        tello.getDroneState().setLowBatteryThreshold(payload[1]);

        log(MessageType.QUERY_LOW_BATTERY_THRESHOLD, String.valueOf(payload[1]));
    }

    private void decodeAttitudeLimitPacket(byte[] payload) {
        byte[] data = Arrays.copyOfRange(payload, 1, payload.length);
        float attitude;

        if (data.length == 3 && data[2] == -56) {
            attitude = 25.0f;
        } else {
            attitude = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        }

        tello.getDroneState().setMaxAttitudeAngle(attitude);

        log(MessageType.QUERY_ATTITUDE_LIMIT, String.valueOf(attitude));
    }

    private void decodeSetHeightLimitPacket(byte[] payload) {
        tello.getPacketSender().sendQueryHeightLimitPacket();

        log(MessageType.SET_HEIGHT_LIMIT, ByteUtils.bytesToHex(payload));
    }

    private void decodeSetAttitudeLimitPacket(byte[] payload) {
        tello.getPacketSender().sendQueryAttitudeLimitPacket();

        log(MessageType.SET_ATTITUDE_LIMIT, ByteUtils.bytesToHex(payload));
    }

    private void decodeSetLowBatteryThresholdPacket(byte[] payload) {
        tello.getPacketSender().sendQueryLowBatteryThresholdPacket();

        log(MessageType.SET_LOW_BATTERY_THRESHOLD, ByteUtils.bytesToHex(payload));
    }

    private void log(MessageType messageType, String data) {
        logger.info(String.format("Received packet! Type: %s; Data: %s", messageType, data));
    }

}
