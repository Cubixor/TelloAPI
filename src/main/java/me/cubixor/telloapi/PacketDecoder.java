package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneStatusListener;
import me.cubixor.telloapi.api.FileReceiver;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.photo.File;
import me.cubixor.telloapi.photo.FileChunk;
import me.cubixor.telloapi.photo.FilePiece;
import me.cubixor.telloapi.photo.ImageDecoder;
import me.cubixor.telloapi.utils.Utils;
import me.cubixor.telloapi.video.VideoManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PacketDecoder {

    private final Drone tello;

    public PacketDecoder(Drone tello) {
        this.tello = tello;
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
                System.out.println("RECEIVE: " + Utils.bytesToHex(fullData));
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
        System.out.println("PASS " + Utils.bytesToHex(payload));
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

        VideoInfo.BitRate bitRate = null;

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
        tello.getVideoInfo().setBitRate(bitRate);
    }


    private void decodeLightStrengthPacket(byte[] payload) {
        tello.resetTimeout();
        if (payload.length != 1) {
            System.out.println("light packet too small");
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
            System.out.println("wifi packet too small");
            return;
        }

        int wifiStrength = payload[0] != 0 ? payload[0] + 10 : 0;
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
        if (payload.length != 24) {
            System.out.println("status payload too small");
            return;
        }

        int height = Utils.connectBytes(payload[0], payload[1]);
        int north_speed = Utils.connectBytes(payload[2], payload[3]);
        int east_speed = Utils.connectBytes(payload[4], payload[5]);
        int ground_speed = Utils.connectBytes(payload[6], payload[7]);
        int fly_time = Utils.connectBytes(payload[8], payload[9]);

        int imu_state = ((payload[10]) & 0x1);
        int pressure_state = ((payload[10] >> 1) & 0x1);
        int down_visual_state = ((payload[10] >> 2) & 0x1);
        int power_state = ((payload[10] >> 3) & 0x1);
        int battery_state = ((payload[10] >> 4) & 0x1);
        int gravity_state = ((payload[10] >> 5) & 0x1);
        int wind_state = ((payload[10] >> 7) & 0x1);

        int imu_calibration_state = payload[11];
        int battery_percentage = payload[12];
        int drone_battery_left = Utils.connectBytes(payload[13], payload[14]);
        int drone_fly_time_left = Utils.connectBytes(payload[15], payload[16]);

        int em_sky = ((payload[17]) & 0x1);
        int em_ground = ((payload[17] >> 1) & 0x1);
        int em_open = ((payload[17] >> 2) & 0x1);
        int drone_hover = ((payload[17] >> 3) & 0x1);
        int outage_recording = ((payload[17] >> 4) & 0x1);
        int battery_low = ((payload[17] >> 5) & 0x1);
        int battery_lower = ((payload[17] >> 6) & 0x1);
        int factory_mode = ((payload[17] >> 7) & 0x1);

        int fly_mode = payload[18];
        int throw_fly_timer = payload[19];
        int camera_state = payload[20];
        int electrical_machinery_state = payload[21];

        int front_in = ((payload[22]) & 0x1);
        int front_out = ((payload[22] >> 1) & 0x1);
        int front_lsc = ((payload[22] >> 2) & 0x1);

        int temperature_height = ((payload[23]) & 0x1);

        tello.getDroneStateManager().setDroneStatus(battery_low, battery_lower, battery_percentage, battery_state, camera_state, down_visual_state, drone_battery_left, drone_fly_time_left, drone_hover, em_open, em_sky, em_ground, east_speed, electrical_machinery_state, factory_mode, fly_mode, fly_time, front_in, front_lsc, front_out, gravity_state, ground_speed, height, imu_calibration_state, imu_state, north_speed, outage_recording, power_state, pressure_state, temperature_height, throw_fly_timer, wind_state);

        for (DroneStatusListener listener : tello.getPacketReceivers()) {
            listener.onStatusPacketReceive(tello.getDroneStateManager());
        }

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
