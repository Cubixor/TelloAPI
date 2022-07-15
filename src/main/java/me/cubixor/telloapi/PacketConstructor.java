package me.cubixor.telloapi;

import me.cubixor.telloapi.api.FlipDirection;
import me.cubixor.telloapi.api.PacketSender;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.utils.Crc;
import me.cubixor.telloapi.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class PacketConstructor implements PacketSender {

    private final Drone tello;

    public PacketConstructor(Drone tello) {
        this.tello = tello;
    }

    private void sendPacket(MessageType messageType) {
        sendPacket(messageType, new byte[0]);
    }

    private void sendPacket(MessageType messageType, byte[] payload) {
        short length = (short) (11 + payload.length);

        byte[] data = new byte[length];

        data[0] = (byte) 0xCC;

        byte[] size = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
                .putShort(length)
                .array();

        data[1] = (byte) (size[0] << 3);
        data[2] = size[1];

        data[3] = Crc.calcCRC8(data, 3);
        data[4] = messageType.getPacketType();

        byte[] messageID = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
                .putShort(messageType.getMessageID())
                .array();

        data[5] = messageID[0];
        data[6] = messageID[1];

        //TODO Sequence ID
        data[7] = data[8] = 0;


        if (payload.length != 0) {
            int pos = 9;
            for (byte b : payload) {
                data[pos] = b;
                pos++;
            }
        }

        byte[] crc = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) Crc.calcCRC16(data, length - 2))
                .array();

        data[length - 2] = crc[0];
        data[length - 1] = crc[1];

        tello.getUdpServer().sendEcho(data);
    }

    @Override
    public void sendConnectPacket() {
        byte[] msg = "conn_req:".getBytes();
        byte[] port = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) 6038).array();

        byte[] data = new byte[11];

        int pos = 0;
        for (byte b : msg) {
            data[pos] = b;
            pos++;
        }

        data[9] = port[0];
        data[10] = port[1];

        tello.getUdpServer().sendEcho(data);
    }

    @Override
    public void sendEmergencyPacket() {
        byte[] msg = "emergency".getBytes();

        tello.getUdpServer().sendEcho(msg);
    }

    @Override
    public void sendQuerySSIDPacket() {
        sendPacket(MessageType.QUERY_SSID);
    }

    @Override
    public void sendQueryPasswordPacket() {
        sendPacket(MessageType.QUERY_PASSWORD);
    }

    @Override
    public void sendQueryRegionPacket() {
        sendPacket(MessageType.QUERY_REGION);
    }

    @Override
    public void sendSetBitratePacket(VideoInfo.BitRate bitrate) {
        byte[] data = new byte[1];
        switch (bitrate) {
            case MBPS_1: {
                data[0] = 1;
                break;
            }
            case MBPS_1_5: {
                data[0] = 2;
                break;
            }
            case MBPS_2: {
                data[0] = 3;
                break;
            }
            case MBPS_3: {
                data[0] = 4;
                break;
            }
            case MBPS_4: {
                data[0] = 5;
                break;
            }
        }
        tello.getVideoInfo().setBitRate(bitrate);
        sendPacket(MessageType.SET_BITRATE, data);
    }

    //TODO Check what is it for
    @Override
    public void sendSetEISPacket(int i) {
        sendPacket(MessageType.SET_EIS, new byte[]{(byte) i});
    }

    @Override
    public void sendStartVideoPacket() {
        sendPacket(MessageType.START_VIDEO);
    }

    @Override
    public void sendTakePicturePacket() {
        sendPacket(MessageType.TAKE_PICTURE);
    }

    //TODO Always 00 00
    @Override
    public void sendQueryBitratePacket() {
        sendPacket(MessageType.QUERY_BITRATE);
    }

    @Override
    public void sendJPEGQualityPacket(boolean highQuality) {
        byte[] payload = new byte[1];
        payload[0] = (byte) (highQuality ? 1 : 0);
        sendPacket(MessageType.QUERY_JPEG_QUALITY, payload);
    }

    @Override
    public void sendQueryVersionPacket() {
        sendPacket(MessageType.QUERY_VERSION);
    }

    //TODO Not tested and not sure what is it for
    @Override
    public void sendSetDateTimePacket(LocalDateTime localDateTime) {
        short year = (short) localDateTime.getYear();
        short month = (short) localDateTime.getMonthValue();
        short day = (short) localDateTime.getDayOfMonth();
        short hour = (short) localDateTime.getHour();
        short minute = (short) localDateTime.getMinute();
        short second = (short) localDateTime.getSecond();
        short millisecond = (short) (localDateTime.getNano() / 1000000);

        byte[] payload = ByteBuffer.allocate(14)
                .putShort(year)
                .putShort(month)
                .putShort(day)
                .putShort(hour)
                .putShort(minute)
                .putShort(second)
                .putShort(millisecond)
                .array();

        sendPacket(MessageType.SET_DATE_TIME, payload);
    }

    //TODO Figure out what time format is
    @Override
    public void sendQueryActivationTimePacket() {
        sendPacket(MessageType.QUERY_ACTIVATION_TIME);
    }

    @Override
    public void sendQueryLoaderVersionPacket() {
        sendPacket(MessageType.QUERY_LOADER_VERSION);
    }

    @Override
    public void sendChangeVideoAspectPacket(VideoInfo.VideoMode videoMode) {
        byte[] payload = new byte[1];
        payload[0] = (byte) (videoMode == VideoInfo.VideoMode.VIDEO ? 1 : 0);
        sendPacket(MessageType.SET_VIDEO_ASPECT, payload);
        //tello.getVideoInfo().getVideoFrameGrabber().applyFrameSize(videoMode);
        tello.getVideoInfo().setVideoMode(videoMode);
    }

    //TODO Check what is it for
    @Override
    public void sendStartRecordingPacket(boolean recording) {
        byte[] data = new byte[1];
        data[0] = (byte) (recording ? 1 : 0);
        sendPacket(MessageType.START_RECORDING, data);
    }

    @Override
    public void sendSetExposurePacket(float exposure) {
        LinkedList<Float> exposureValues = new LinkedList<>();
        for (int i = 0; i < VideoInfo.getExposureValues().length; i++) {
            exposureValues.add(VideoInfo.getExposureValues()[i]);
        }

        if (!exposureValues.contains(exposure)) {
            throw new IllegalArgumentException("Invalid exposure value!");
        }

        sendSetExposurePacket(exposureValues.indexOf(exposure) - 9);
    }

    @Override
    public void sendSetExposurePacket(int exposure) {
        byte[] data = new byte[]{(byte) exposure};

        tello.getVideoInfo().setExposure(exposure);
        sendPacket(MessageType.EXPOSURE_VALUES, data);
    }


    @Override
    public void sendSticksPacket(int roll, int pitch, int throttle, int yaw, boolean fastMode) {
        byte[] sticks;

        long axis1 = roll & 0x7ff;
        long axis2 = pitch & 0x7ff;
        long axis3 = throttle & 0x7ff;
        long axis4 = yaw & 0x7ff;
        long axis5 = fastMode ? 1 : 0;
        long packed = (axis1 | (axis2 << 11)) | (axis3 << 22) | (axis4 << 33) | (axis5 << 44);
        sticks = Utils.trim(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(packed).array());

        byte[] date;
        LocalDateTime dt = LocalDateTime.now();
        byte hour = (byte) dt.getHour();
        byte minute = (byte) dt.getMinute();
        byte second = (byte) dt.getSecond();
        short milli = (short) dt.getNano();
        date = ByteBuffer.allocate(5).put(hour).put(minute).put(second).put(ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(milli)).array();

        byte[] data = ByteBuffer.allocate(11).put(sticks).put(date).array();

        sendPacket(MessageType.SET_STICKS, data);
    }

    @Override
    public void sendTakeOffPacket() {
        sendPacket(MessageType.TAKEOFF);
    }

    @Override
    public void sendLandPacket() {
        byte[] data = {0x00};
        sendPacket(MessageType.LAND, data);
    }

    @Override
    public void sendSetHeightLimitPacket(short height) {
        byte[] data = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(height).array();
        sendPacket(MessageType.SET_HEIGHT_LIMIT, data);
    }

    @Override
    public void sendFlipPacket(FlipDirection flipDirection) {
        byte[] data = new byte[1];
        data[0] = flipDirection.getData();
        sendPacket(MessageType.FLIP, data);
    }

    @Override
    public void sendThrowTakeoffPacket() {
        sendPacket(MessageType.THROW_TAKEOFF);
    }

    @Override
    public void sendPalmLandPacket() {
        sendPacket(MessageType.PALM_LAND);
    }

    @Override
    public void sendFileSizePacket() {
        sendPacket(MessageType.FILE_SIZE, new byte[]{0});
    }

    @Override
    public void sendFileDataPacket(boolean done, int fileID, int filePiece) {
        byte doneByte = (byte) (done ? 1 : 0);
        byte[] payload = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).put(doneByte).putShort((short) fileID).putInt(filePiece).array();
        sendPacket(MessageType.FILE_DATA, payload);
    }

    @Override
    public void sendFileCompletePacket(int fileID, int fileSize) {
        byte[] payload = Utils.trim(ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putShort((short) fileID).putInt(fileSize).array());
        sendPacket(MessageType.FILE_DONE, payload);
    }

    //TODO To check
    @Override
    public void sendStartSmartVideoPacket(VideoInfo.SmartVideoMode smartVideoMode, boolean start) {
        byte[] data = new byte[1];
        byte mode = smartVideoMode.getData();
        byte flag = (byte) (start ? 1 : 0);

        data[0] = (byte) (mode << 2 | flag);
        sendPacket(MessageType.START_SMART_VIDEO, data);
    }

    @Override
    public void sendLogHeaderAckPacket(short s) {
        byte[] data = ByteBuffer.allocate(3).put((byte) 0).order(ByteOrder.LITTLE_ENDIAN).putShort(s).array();

        sendPacket(MessageType.LOG_HEADER, data);
    }

    @Override
    public void sendLogConfigAckPacket(short s, int s2) {
        byte[] data = ByteBuffer.allocate(7).put((byte) 0).order(ByteOrder.LITTLE_ENDIAN).putShort(s).putInt(s2).array();

        sendPacket(MessageType.LOG_CONFIG, data);
    }

    @Override
    public void sendBounceModePacket(boolean start) {
        byte[] data = new byte[1];
        data[0] = (byte) (start ? 0x30 : 0x31);
        sendPacket(MessageType.BOUNCE, data);
    }

    @Override
    public void sendSetLowBatteryThresholdPacket(short battery) {
        byte[] data = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(battery).array();
        sendPacket(MessageType.SET_LOW_BATTERY_THRESHOLD, data);
    }

    @Override
    public void sendQueryHeightLimitPacket() {
        sendPacket(MessageType.QUERY_HEIGHT_LIMIT);
    }

    @Override
    public void sendQueryLowBatteryThresholdPacket() {
        sendPacket(MessageType.QUERY_LOW_BATTERY_THRESHOLD);
    }

    @Override
    public void sendQueryAttitudeLimitPacket() {
        sendPacket(MessageType.QUERY_ATTITUDE_LIMIT);
    }

    @Override
    public void sendSetAttitudeLimitPacket(float attitude) {
        byte[] data = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(attitude).array();
        sendPacket(MessageType.SET_ATTITUDE_LIMIT, data);
    }

    @Override
    public void sendStartMotorsPacket() {
        tello.resetAxis = 20;
        tello.setAxis(-1, -1, -1, 1);
    }

    @Override
    public void sendStopMotorsPacket() {
        tello.resetAxis = 20;
        tello.setAxis(0, 0, -1, 0);
    }


}
