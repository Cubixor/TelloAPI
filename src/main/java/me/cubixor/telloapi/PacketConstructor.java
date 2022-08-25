package me.cubixor.telloapi;

import me.cubixor.telloapi.api.FlipDirection;
import me.cubixor.telloapi.api.PacketSender;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.utils.ByteUtils;
import me.cubixor.telloapi.utils.Crc;

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

        tello.getUdpServer().sendPacket(data);
    }

    /**
     * Sends connection request packet. It has to be done before sending or receiving any other packets from drone.
     * <p>
     * If the packet was delivered successfully, drone should respond with connection accept packet.
     */
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

        tello.getUdpServer().sendPacket(data);
    }

    /**
     * Emergency packet, not available in the original app. Makes all motors immediately stop regardless of the circumstances.
     * Use with caution, as this may cause the drone to break.
     */
    @Override
    public void sendEmergencyPacket() {
        byte[] msg = "emergency".getBytes();

        tello.getUdpServer().sendPacket(msg);
    }

    /**
     * Sends a query for the drone Wi-Fi SSID
     */
    @Override
    public void sendQuerySSIDPacket() {
        sendPacket(MessageType.QUERY_SSID);
    }

    /**
     * Sends a query for the drone Wi-Fi password
     */
    @Override
    public void sendQueryPasswordPacket() {
        sendPacket(MessageType.QUERY_PASSWORD);
    }

    /**
     * Sends a query for the drone Wi-Fi region
     */
    @Override
    public void sendQueryRegionPacket() {
        sendPacket(MessageType.QUERY_REGION);
    }

    /**
     * Changes video bitrate.
     *
     * @param bitrate video bitrate, choose between MBPS_1, MBPS_1_5, MBPS_2, MBPS_3, MBPS_4 and AUTO.
     * @see me.cubixor.telloapi.api.VideoInfo.BitRate
     */
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

    /**
     * Not sure if it's working.
     * <p>
     * According to the description in the original app it should "Dynamically adjust the bit rate" or "Adjust Dynamic Frame Rate" (it is described differently in different places).
     * <p>
     * Looks like it was supposed to be in the settings menu, but wasn't used in the end (and that's why it's not present in the original app).
     *
     * @param enable true for "HD Mode" and false for "Smooth Mode" (according to the original app's code)
     */
    @Override
    public void sendSetDynAdjRatePacket(boolean enable) {
        sendPacket(MessageType.SET_DYN_ADJ_RATE, new byte[]{(byte) (enable ? 1 : 0)});
    }


    /**
     * Most probably it's not working.
     * <p>
     * It should enable and disable EIS (electronic image stabilization) but it wasn't working for me.
     * <p>
     * It looks like at first there was intention to create separate setting in the settings menu for turning EIS on and off but in the end it was removed.
     * Instead of this EIS is automatically turned on for slow (video) mode and off for fast (sport) mode.
     *
     * @param enable true for enabling EIS and false for disabling it
     */
    @Override
    public void sendSetEISPacket(boolean enable) {
        sendPacket(MessageType.SET_EIS, new byte[]{(byte) (enable ? 1 : 0)});
    }

    /**
     * It's actually a request for an iFrame, but it's also used to start receiving video stream.
     * <p>
     * If the video is glitching requesting an iFrame should help.
     */
    @Override
    public void sendStartVideoPacket() {
        sendPacket(MessageType.START_VIDEO);
    }


    /**
     * Takes a picture which is then transferred to the api as a {@link me.cubixor.telloapi.photo.File}
     * <p>
     * Works only when {@link me.cubixor.telloapi.api.VideoInfo.VideoMode} is set to PHOTO. May cause a lag when sending the photo.
     */
    @Override
    public void sendTakePicturePacket() {
        sendPacket(MessageType.TAKE_PICTURE);
    }

    /**
     * Sends a query for the drone bitrate.
     * <p>
     * Not working - data in the response is always 00 00
     */
    @Override
    public void sendQueryBitratePacket() {
        sendPacket(MessageType.QUERY_BITRATE);
    }

    /**
     * Sets taken picture quality. Higher quality means more detailed pictures but more time to transfer them from a drone to the api.
     *
     * @param highQuality true for higher quality and false for lower quality
     */
    @Override
    public void sendJPEGQualityPacket(boolean highQuality) {
        byte[] payload = new byte[1];
        payload[0] = (byte) (highQuality ? 1 : 0);
        sendPacket(MessageType.QUERY_JPEG_QUALITY, payload);
    }


    /**
     * Sends a query for the drone version.
     */
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

        byte[] payload = ByteBuffer.allocate(15)
                .put((byte) 0)
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

    /**
     * Sends a query for the drone activation time.
     */
    @Override
    public void sendQueryActivationTimePacket() {
        sendPacket(MessageType.QUERY_ACTIVATION_TIME);
    }


    /**
     * Sends a query for the drone loader version.
     */
    @Override
    public void sendQueryLoaderVersionPacket() {
        sendPacket(MessageType.QUERY_LOADER_VERSION);
    }

    /**
     * Change the video aspect.
     * <p>
     * Use photo mode to receive video in 960x720 resolution and be able to take photos. Use video mode to receive video in 1280x720 resolution.
     *
     * @param videoMode choose between PHOTO and VIDEO
     */
    @Override
    public void sendChangeVideoAspectPacket(VideoInfo.VideoMode videoMode) {
        byte[] payload = new byte[1];
        payload[0] = (byte) (videoMode == VideoInfo.VideoMode.VIDEO ? 1 : 0);
        sendPacket(MessageType.SET_VIDEO_ASPECT, payload);
        tello.getVideoInfo().setVideoMode(videoMode);
    }

    /**
     * No idea what it does
     * TODO Check what it does
     */
    @Override
    public void sendStartRecordingPacket(boolean recording) {
        byte[] data = new byte[1];
        data[0] = (byte) (recording ? 1 : 0);
        sendPacket(MessageType.START_RECORDING, data);
    }

    /**
     * Sets video exposure.
     * Available exposure values (0 means automatic):
     * <pre>
     * {@code -3.0, -2.7, -2.3, -2.0, -1.7, -1.3, -1.0, -0.7, -0.3,
     * 0, 0.3, 0.7, 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0}
     * </pre>
     *
     * @param exposure video exposure chosen from the list above
     * @see PacketConstructor#sendSetExposurePacket(int)
     */
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

    /**
     * Sets video exposure. Use number between -9 and 9.
     *
     * @param exposure video exposure
     * @see PacketConstructor#sendSetExposurePacket(float)
     */
    @Override
    public void sendSetExposurePacket(int exposure) {
        byte[] data = new byte[]{(byte) exposure};

        tello.getVideoInfo().setExposure(exposure);
        sendPacket(MessageType.EXPOSURE_VALUES, data);
    }

    /**
     * Packet with joysticks position and flight mode (fast/slow), has to be sent every 20ms.
     * <p>
     * Use floats between -1.0 and 1.0 for roll, pitch throttle and yaw.
     * <p>
     * Fast (sport) mode means no image stabilization, wider viewing angle on received video, higher maximum speed and faster reaction to joystick moves.
     * <p>
     * Slow (video) mode means electronic image stabilization and therefore cropped video, slower maximum speed and slower reaction to joystick moves (for video stability).
     *
     * @param roll     for flying left and right
     * @param pitch    for flying forward and backward
     * @param throttle for flying up and down
     * @param yaw      for rotating the drone left and right
     * @param fastMode true for fast (sport) mode and false for slow (video) mode
     */
    @Override
    public void sendSticksPacket(float roll, float pitch, float throttle, float yaw, boolean fastMode) {
        int a1 = (int) (1024 + 660 * roll);
        int a2 = (int) (1024 + 660 * pitch);
        int a3 = (int) (1024 + 660 * throttle);
        int a4 = (int) (1024 + 660 * yaw);
        sendSticksPacket(a1, a2, a3, a4, fastMode);
    }


    /**
     * @see PacketConstructor#sendSticksPacket(float, float, float, float, boolean)
     */
    @Override
    public void sendSticksPacket(int roll, int pitch, int throttle, int yaw, boolean fastMode) {
        byte[] sticks;

        long axis1 = roll & 0x7ff;
        long axis2 = pitch & 0x7ff;
        long axis3 = throttle & 0x7ff;
        long axis4 = yaw & 0x7ff;
        long axis5 = fastMode ? 1 : 0;
        long packed = (axis1 | (axis2 << 11)) | (axis3 << 22) | (axis4 << 33) | (axis5 << 44);
        sticks = ByteUtils.trim(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(packed).array());

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

    /**
     * Tell the drone to take-off. Keep in mind that drone may not take off, for example because of low battery or an error.
     */
    @Override
    public void sendTakeOffPacket() {
        sendPacket(MessageType.TAKEOFF);
    }

    /**
     * Tell the drone to land. Landing may be interrupted, for example if the throttle axis is not set to neutral.
     * TODO Check what this one parameter does
     *
     * @param bool one unknown parameter
     */
    @Override
    public void sendLandPacket(boolean bool) {
        byte[] data = {(byte) (bool ? 1 : 0)};
        sendPacket(MessageType.LAND, data);
    }

    /**
     * Sets drone height limit. Height limit must not be greater than 30.
     *
     * @param height height limit in meters
     */
    @Override
    public void sendSetHeightLimitPacket(short height) {
        byte[] data = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(height).array();
        sendPacket(MessageType.SET_HEIGHT_LIMIT, data);
    }

    /**
     * Tells the drone to flip in specified direction. Keep in mind that drone ay not flip, for example if the battery is lower than 50% or in other unexplained circumstances.
     * <p>
     * Choose flip direction between FORWARD, LEFT, BACKWARD, RIGHT, FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT
     *
     * @param flipDirection direction to flip the drone
     * @see FlipDirection
     */
    @Override
    public void sendFlipPacket(FlipDirection flipDirection) {
        byte[] data = new byte[1];
        data[0] = flipDirection.getData();
        sendPacket(MessageType.FLIP, data);
    }

    /**
     * Makes the drone start throw take-off procedure. The propellers should start slightly spinning and after throwing the drone into the air it should start hovering.
     */
    @Override
    public void sendThrowTakeoffPacket() {
        sendPacket(MessageType.THROW_TAKEOFF);
    }

    /**
     * Makes the drone start palm land procedure. If you put your hand below the drone during the next 5 seconds it should land on it.
     */
    @Override
    public void sendPalmLandPacket() {
        sendPacket(MessageType.PALM_LAND);
    }

    /**
     * File size packet must be sent after receiving packet of the same type, to start receiving the photo.
     */
    @Override
    public void sendFileSizePacket() {
        sendPacket(MessageType.FILE_SIZE, new byte[]{0});
    }


    /**
     * File data packet must be sent after all FilePieces of the FileChunk have been received to accept them.
     *
     * @param done      true for OK, false for NOT OK
     * @param fileID    id of the file which is being transferred
     * @param filePiece transferred file piec id
     */
    @Override
    public void sendFileDataPacket(boolean done, int fileID, int filePiece) {
        byte doneByte = (byte) (done ? 1 : 0);
        byte[] payload = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).put(doneByte).putShort((short) fileID).putInt(filePiece).array();
        sendPacket(MessageType.FILE_DATA, payload);
    }

    /**
     * File complete packet must be sent after the whole photo has been received.
     *
     * @param fileID   received file id
     * @param fileSize received file size
     */
    @Override
    public void sendFileCompletePacket(int fileID, int fileSize) {
        byte[] payload = ByteUtils.trim(ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putShort((short) fileID).putInt(fileSize).array());
        sendPacket(MessageType.FILE_DONE, payload);
    }

    /**
     * Start one of three smart video modes
     * <p>
     * VIDEO_360 - drone will spin 360 degrees in place
     * CIRCLE - drone will fly in a circle shape
     * UP_AND_OUT - drone will fly upwards and backwards
     *
     * @param smartVideoMode VIDEO_360, CIRCLE or UP_AND_OUT
     * @param start          true to start, false to stop
     * @see me.cubixor.telloapi.api.VideoInfo.SmartVideoMode
     */
    @Override
    public void sendStartSmartVideoPacket(VideoInfo.SmartVideoMode smartVideoMode, boolean start) {
        byte[] data = new byte[1];
        byte mode = smartVideoMode.getData();
        byte flag = (byte) (start ? 1 : 0);

        data[0] = (byte) (mode << 2 | flag);
        sendPacket(MessageType.START_SMART_VIDEO, data);
    }

    /**
     * Needs to be sent after receiving log header to start receiving logs.
     *
     * @param seqId sequence ID, passed in the received log header packet
     */
    @Override
    public void sendLogHeaderAckPacket(short seqId) {
        byte[] data = ByteBuffer.allocate(3).put((byte) 0).order(ByteOrder.LITTLE_ENDIAN).putShort(seqId).array();

        sendPacket(MessageType.LOG_HEADER, data);
    }


    /**
     * Needs to be sent after receiving log config packet to accept it.
     *
     * @param s  some data from received config packet
     * @param s2 other data from received config packet
     */
    @Override
    public void sendLogConfigAckPacket(short s, int s2) {
        byte[] data = ByteBuffer.allocate(7).put((byte) 0).order(ByteOrder.LITTLE_ENDIAN).putShort(s).putInt(s2).array();

        sendPacket(MessageType.LOG_CONFIG, data);
    }

    /**
     * Start or stop bounce mode.
     * <p>
     * The drone will automatically fly up and down within 0.5 and 1.2 meters of a flat surface below.
     *
     * @param start true to start and false to stop
     */
    @Override
    public void sendBounceModePacket(boolean start) {
        byte[] data = new byte[1];
        data[0] = (byte) (start ? 0x30 : 0x31);
        sendPacket(MessageType.BOUNCE, data);
    }

    /**
     * Sets low battery threshold. Certain command are ignored below this threshold.
     *
     * @param battery low battery threshold in percents (eg. 15)
     */
    @Override
    public void sendSetLowBatteryThresholdPacket(short battery) {
        byte[] data = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(battery).array();
        sendPacket(MessageType.SET_LOW_BATTERY_THRESHOLD, data);
    }

    /**
     * Sends a query for the drone height limit.
     */
    @Override
    public void sendQueryHeightLimitPacket() {
        sendPacket(MessageType.QUERY_HEIGHT_LIMIT);
    }

    /**
     * Sends a query for the drone low battery threshold.
     */
    @Override
    public void sendQueryLowBatteryThresholdPacket() {
        sendPacket(MessageType.QUERY_LOW_BATTERY_THRESHOLD);
    }

    /**
     * Sends a query for the drone attitude limit.
     */
    @Override
    public void sendQueryAttitudeLimitPacket() {
        sendPacket(MessageType.QUERY_ATTITUDE_LIMIT);
    }

    /**
     * Sends the attitude limit in degrees.
     *
     * @param attitude attitude in degrees, max 25
     */
    @Override
    public void sendSetAttitudeLimitPacket(float attitude) {
        byte[] data = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(attitude).array();
        sendPacket(MessageType.SET_ATTITUDE_LIMIT, data);
    }

    @Override
    public void sendStartMotorsPacket() {
        tello.getDroneAxis().resetLater();
        tello.getDroneAxis().setAxis(-1, -1, -1, 1);
    }

    @Override
    public void sendStopMotorsPacket() {
        tello.getDroneAxis().resetLater();
        tello.getDroneAxis().setAxis(0, 0, -1, 0);
    }


}
