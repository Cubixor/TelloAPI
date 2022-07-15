package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneStatusListener;
import me.cubixor.telloapi.api.FileReceiver;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.logs.LogDataManager;
import me.cubixor.telloapi.photo.File;
import me.cubixor.telloapi.photo.FileChunk;
import me.cubixor.telloapi.photo.FilePiece;
import me.cubixor.telloapi.photo.ImageDecoder;
import me.cubixor.telloapi.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PacketDecoder {

    private final Drone tello;
    LogDataManager logDataManager;

    public PacketDecoder(Drone tello) {
        this.tello = tello;
        this.logDataManager = new LogDataManager(tello);
    }

    public void handlePacket(byte[] data) {
        byte[] trimmed = Utils.trim(data);

        byte prefix = trimmed[0];
        if (prefix == (byte) 0xCC) {
            short messageID = ByteBuffer.wrap(Arrays.copyOfRange(trimmed, 5, 7)).order(ByteOrder.LITTLE_ENDIAN).getShort();
            MessageType messageType = MessageType.getMessageType(messageID);
            byte[] payload = trimmed.length > 11 ? Arrays.copyOfRange(trimmed, 9, trimmed.length - 2) : new byte[0];

            //System.out.println(messageType.toString() + " " + Utils.bytesToHex(payload));

            decodePacket(messageType, payload, trimmed);
        } else if (Utils.startsWith(trimmed, "conn_ack:".getBytes())) {
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
            case QUERY_LOADER_VERSION: {
                decodeLoaderVersionPacket(payload);
                break;
            }
            case QUERY_BITRATE: {
                System.out.println("BITRATE " + Utils.bytesToHex(fullData));
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
                //System.out.println("RECEIVE: " + Utils.bytesToHex(fullData));
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
        //System.out.println("PASS " + Utils.bytesToHex(payload));
        byte[] data = Arrays.copyOfRange(payload, 2, payload.length);
        String password = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setWifiPassword(password);
    }

    private void decodeRegionPacket(byte[] payload) {
        byte[] data = Utils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String region = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setWifiRegion(region);
    }

    private void decodeVersionPacket(byte[] payload) {
        byte[] data = Utils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String version = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setVersion(version);
    }

    private void decodeLoaderVersionPacket(byte[] payload) {
        byte[] data = Utils.trim(Arrays.copyOfRange(payload, 1, payload.length));
        String version = new String(data, StandardCharsets.UTF_8);
        tello.getDroneStateManager().setLoaderVersion(version);
    }

    //Not working
    private void decodeBitratePacket(byte[] payload) {
        System.out.println("bitrate" + Utils.bytesToHex(payload));
        //byte[] data = Utils.trim(Arrays.copyOfRange(payload, 1, payload.length));

/*        VideoInfo.BitRate bitRate = null;

        switch (payload[0]) {
            case 0: {
                bitRate = VideoManager.BitRate.AUTO;
                break;
            }
            case 1: {
                bitRate = VideoManager.BitRate.MBPS_1;
                break;
            }
            case 2: {
                bitRate = VideoManager.BitRate.MBPS_1_5;
                break;
            }
            case 3: {
                bitRate = VideoManager.BitRate.MBPS_2;
                break;
            }
            case 4: {
                bitRate = VideoManager.BitRate.MBPS_3;
                break;
            }
            case 5: {
                bitRate = VideoManager.BitRate.MBPS_4;
                break;
            }
        }

        System.out.println(bitRate);
        tello.getVideoInfo().setBitRate(bitRate);*/
    }


    private void decodeLightStrengthPacket(byte[] payload) {
        tello.resetTimeout();
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
        tello.resetTimeout();
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
        System.out.println("takeoff " + Utils.bytesToHex(payload));
    }

    private void decodeLandPacket(byte[] payload) {
        System.out.println("land " + Utils.bytesToHex(payload));
    }

    private void decodeStatusPacket(byte[] payload) {
        tello.resetTimeout();

        //TODO Status packet comes in different sizes
        if (payload.length != 24) {
            return;
        }

        tello.getDroneStateManager().updateDroneStatus(payload);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onStatusPacketReceive(tello.getDroneStateManager());
        }

        //System.out.println("fly time left " + test2(test1(payload, 13,2)));
        //System.out.println("battery left " +  payload[15] +" "+ payload[16]+ " " + Arrays.toString(test1(payload, 15, 2)));

        //System.out.println("STATUS " + Utils.bytesToHex(payload));

    }

    private void decodeFileSizePacket(byte[] payload) {
        int fileSize = Utils.connectBytes(Arrays.copyOfRange(payload, 1, 5));
        int fileID = Utils.connectBytes(Arrays.copyOfRange(payload, 5, 7));

        tello.getPendingFiles().put(fileID, new File(fileID, fileSize));
        tello.getPacketSender().sendFileSizePacket();

        System.out.println("FILESIZE  " + tello.getPendingFiles().get(fileID));
    }

    private void decodeFileDataPacket(byte[] payload) {
        int fileID = Utils.connectBytes(Arrays.copyOfRange(payload, 0, 2));
        int filePieceID = Utils.connectBytes(Arrays.copyOfRange(payload, 2, 6));
        int fileChunkID = Utils.connectBytes(Arrays.copyOfRange(payload, 6, 10));
        int fileChunkLength = Utils.connectBytes(Arrays.copyOfRange(payload, 10, 12));
        byte[] data = Arrays.copyOfRange(payload, 12, payload.length);

        File file = tello.getPendingFiles().get(fileID);

        if (file.isReceived()) {
            return;
        }

        if (!file.getFilePieces().containsKey(filePieceID)) {
            file.getFilePieces().put(filePieceID, new FilePiece(filePieceID));
        }

        FilePiece filePiece = file.getFilePieces().get(filePieceID);

        if (filePiece.getFileChunks()[fileChunkID - 8 * filePieceID] == null) {
            filePiece.getFileChunks()[fileChunkID - 8 * filePieceID] = new FileChunk(fileChunkID, fileChunkLength, data);
        }

        file.addCurrentSize(fileChunkLength);


        if (file.getCurrentSize() >= file.getFileSize()) {
            tello.getPacketSender().sendFileDataPacket(true, fileID, filePieceID);
            tello.getPacketSender().sendFileCompletePacket(fileID, file.getFileSize());
            file.setReceived(true);

            System.out.println(file);

            ImageDecoder imageDecoder = new ImageDecoder();
            byte[] b = imageDecoder.fileToByteArray(file);
/*
            BufferedImage image = imageDecoder.decodeImage(b);
            System.out.println(image);
*/

            for (FileReceiver fileReceiver : tello.getFileReceivers()) {
                fileReceiver.onPhotoReceived(b);
            }

        } else if (filePiece.getFileChunks()[7] != null) {
            tello.getPacketSender().sendFileDataPacket(false, fileID, filePieceID);
        }

        System.out.println("FILE:" + fileID + "  FILESIZE:" + file.getFileSize() + "  FILECURRENTSIZE:" + file.getCurrentSize() + "   FILEPIECE:" + filePieceID + "  FILECHUNK:" + fileChunkID + " CHUNKSIZE:" + fileChunkLength + " ACTUALCHUNKSIZE:" + data.length);
    }

    private void decodeSmartVideoStatus(byte[] payload) {
        System.out.println("SMARTVIDEO " + Utils.bytesToHex(payload));

        byte videoModeID = (byte) (payload[0] >> 5);
        VideoInfo.SmartVideoMode videoMode = VideoInfo.SmartVideoMode.getByID(videoModeID);
        boolean running = (payload[0] >> 3 & 1) == 1;

        tello.getVideoInfo().setSmartVideoMode(videoMode);
        tello.getVideoInfo().setSmartVideoRunning(running);

        System.out.println("SMARTVIDEOMODE " + tello.getVideoInfo().getSmartVideoMode());
        System.out.println("SMARTVIDEORUNNING " + tello.getVideoInfo().isSmartVideoRunning());
    }

    private void decodeLogHeaderPacket(byte[] payload) {
        System.out.println("LOG HEADER:    SIZE: " + payload.length + "   DATA: " + Utils.bytesToHex(payload));
        short seqId = (short) Utils.connectBytes(payload[0], payload[1]);
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
        System.out.println("LOG CONFIG:    SIZE: " + payload.length + "   DATA: " + Utils.bytesToHex(payload));

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
