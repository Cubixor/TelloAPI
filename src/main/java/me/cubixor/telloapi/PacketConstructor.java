package me.cubixor.telloapi;

import me.cubixor.telloapi.api.FlipDirection;
import me.cubixor.telloapi.api.video.BitRate;
import me.cubixor.telloapi.api.video.SmartVideoMode;
import me.cubixor.telloapi.api.video.VideoMode;
import me.cubixor.telloapi.utils.ByteUtils;
import me.cubixor.telloapi.utils.Crc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;

public class PacketConstructor {

    private final Drone tello;
    private final Logger logger = LogManager.getLogger(PacketConstructor.class);

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

    public void sendEmergencyPacket() {
        byte[] msg = "emergency".getBytes();

        tello.getUdpServer().sendPacket(msg);

        logger.warn("Sent emergency packet!");
    }

    /**
     * Sends a query for the drone Wi-Fi SSID
     */

    public void sendQuerySSIDPacket() {
        sendPacket(MessageType.QUERY_SSID);

        log(MessageType.QUERY_SSID);
    }

    /**
     * Updates Wi-Fi SSID, changes visible after restart
     *
     * @param ssid Wi-Fi SSID
     */
    public void sendSetSSIDPacket(String ssid) {
        byte[] msg = ssid.getBytes();

        sendPacket(MessageType.SET_SSID, msg);

        log(MessageType.SET_SSID, ssid);
    }

    /**
     * Sends a query for the drone Wi-Fi password
     */

    public void sendQueryPasswordPacket() {
        sendPacket(MessageType.QUERY_PASSWORD);

        log(MessageType.QUERY_PASSWORD);
    }

    /**
     * Updates Wi-Fi password, changes visible after restart
     *
     * @param password Wi-Fi password
     */
    public void sendSetPasswordPacket(String password) {
        byte[] msg = password.getBytes();

        sendPacket(MessageType.SET_PASSWORD, msg);

        log(MessageType.SET_PASSWORD, password);
    }

    /**
     * Sends a query for the drone Wi-Fi region
     */

    public void sendQueryRegionPacket() {
        sendPacket(MessageType.QUERY_REGION);

        log(MessageType.QUERY_REGION);
    }

    /**
     * Updates Wi-Fi region changes visible after restart
     *
     * @param region Wi-Fi region
     */

    public void sendSetRegionPacket(String region) {
        byte[] msg = region.getBytes();

        sendPacket(MessageType.SET_REGION, msg);

        log(MessageType.SET_REGION, region);
    }

    /**
     * Changes video bitrate.
     *
     * @param bitrate video bitrate, choose between MBPS_1, MBPS_1_5, MBPS_2, MBPS_3, MBPS_4 and AUTO.
     * @see BitRate
     */

    public void sendSetBitratePacket(BitRate bitrate) {
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
        sendPacket(MessageType.SET_BITRATE, data);

        log(MessageType.SET_BITRATE, bitrate.toString());
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

    public void sendSetDynAdjRatePacket(boolean enable) {
        sendPacket(MessageType.SET_DYN_ADJ_RATE, new byte[]{(byte) (enable ? 1 : 0)});

        log(MessageType.SET_DYN_ADJ_RATE, String.valueOf(enable));
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

    public void sendSetEISPacket(boolean enable) {
        sendPacket(MessageType.SET_EIS, new byte[]{(byte) (enable ? 1 : 0)});

        log(MessageType.SET_EIS, String.valueOf(enable));
    }

    /**
     * It's actually a request for an iFrame, but it's also used to start receiving video stream.
     * <p>
     * If the video is glitching requesting an iFrame should help.
     */

    public void sendStartVideoPacket() {
        sendPacket(MessageType.START_VIDEO);

        log(MessageType.START_VIDEO);
    }


    /**
     * Takes a picture which is then transferred to the api as a {@link me.cubixor.telloapi.photo.File}
     * <p>
     * Works only when {@link VideoMode} is set to PHOTO. May cause a lag when sending the photo.
     */

    public void sendTakePicturePacket() {
        sendPacket(MessageType.TAKE_PICTURE);

        log(MessageType.TAKE_PICTURE);
    }

    /**
     * Sends a query for the drone bitrate.
     * <p>
     * Not working - data in the response is always 00 00
     */

    public void sendQueryBitratePacket() {
        sendPacket(MessageType.QUERY_BITRATE);

        log(MessageType.QUERY_BITRATE);
    }

    /**
     * Sets taken picture quality. Higher quality means more detailed pictures but more time to transfer them from a drone to the api.
     *
     * @param highQuality true for higher quality and false for lower quality
     */

    public void sendJPEGQualityPacket(boolean highQuality) {
        byte[] payload = new byte[1];
        payload[0] = (byte) (highQuality ? 1 : 0);
        sendPacket(MessageType.QUERY_JPEG_QUALITY, payload);

        log(MessageType.QUERY_JPEG_QUALITY, String.valueOf(highQuality));
    }


    /**
     * Sends a query for the drone version.
     */

    public void sendQueryVersionPacket() {
        sendPacket(MessageType.QUERY_VERSION);

        log(MessageType.QUERY_VERSION);
    }

    /**
     * Send current date and time to drone, after receiving request packet of the same type.
     * <p>
     * Not sure what is it for.
     *
     * @param localDateTime current date and time, use {@link LocalDateTime#now()}
     */
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

        log(MessageType.SET_DATE_TIME, localDateTime.toString());
    }

    /**
     * Sends a query for the drone activation time.
     */

    public void sendQueryActivationTimePacket() {
        sendPacket(MessageType.QUERY_ACTIVATION_TIME);

        log(MessageType.QUERY_ACTIVATION_TIME);
    }


    /**
     * Sends a query for the drone loader version.
     */

    public void sendQueryLoaderVersionPacket() {
        sendPacket(MessageType.QUERY_LOADER_VERSION);

        log(MessageType.QUERY_LOADER_VERSION);
    }

    /**
     * Change the video aspect.
     * <p>
     * Use photo mode to receive video in 960x720 resolution and be able to take photos. Use video mode to receive video in 1280x720 resolution.
     *
     * @param videoMode choose between PHOTO and VIDEO
     */

    public void sendChangeVideoAspectPacket(VideoMode videoMode) {
        byte[] payload = new byte[1];
        payload[0] = (byte) (videoMode == VideoMode.VIDEO ? 1 : 0);
        sendPacket(MessageType.SET_VIDEO_ASPECT, payload);

        log(MessageType.SET_VIDEO_ASPECT, videoMode.toString());
    }

    /**
     * No idea what it does
     * TODO Check what it does
     */

    public void sendStartRecordingPacket(boolean recording) {
        byte[] data = new byte[1];
        data[0] = (byte) (recording ? 1 : 0);
        sendPacket(MessageType.START_RECORDING, data);

        log(MessageType.START_RECORDING, String.valueOf(recording));
    }

    /**
     * Sets video exposure. Use number between -9 and 9, corresponding to the exposure value from the list (0 means automatic):
     * <pre>
     * {@code -3.0, -2.7, -2.3, -2.0, -1.7, -1.3, -1.0, -0.7, -0.3,
     * 0, 0.3, 0.7, 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0}</pre>
     *
     * @param exposure video exposure
     */

    public void sendSetExposurePacket(int exposure) {
        byte[] data = new byte[]{(byte) exposure};

        sendPacket(MessageType.EXPOSURE_VALUES, data);

        log(MessageType.EXPOSURE_VALUES, String.valueOf(exposure));
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

    public void sendSticksPacket(float roll, float pitch, float throttle, float yaw, boolean fastMode) {
        int a1 = (int) (1024 + 660 * roll);
        int a2 = (int) (1024 + 660 * pitch);
        int a3 = (int) (1024 + 660 * throttle);
        int a4 = (int) (1024 + 660 * yaw);
        sendSticksPacket(a1, a2, a3, a4, fastMode);

        log(MessageType.SET_STICKS, "Roll: " + roll + " Pitch: " + pitch + " Throttle: " + throttle + " Yaw: " + yaw + " Fast mode: " + fastMode);
    }


    /**
     * @see PacketConstructor#sendSticksPacket(float, float, float, float, boolean)
     */

    private void sendSticksPacket(int roll, int pitch, int throttle, int yaw, boolean fastMode) {
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
    public void sendTakeOffPacket() {
        sendPacket(MessageType.TAKEOFF);

        log(MessageType.TAKEOFF);
    }

    /**
     * Tell the drone to land. Landing may be interrupted, for example if the throttle axis is not set to neutral.
     * TODO Check what this one parameter does
     *
     * @param bool one unknown parameter
     */

    public void sendLandPacket(boolean bool) {
        byte[] data = {(byte) (bool ? 1 : 0)};
        sendPacket(MessageType.LAND, data);

        log(MessageType.LAND, String.valueOf(bool));
    }

    /**
     * Sets drone height limit. Height limit must not be greater than 30.
     *
     * @param height height limit in meters
     */

    public void sendSetHeightLimitPacket(short height) {
        byte[] data = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(height).array();
        sendPacket(MessageType.SET_HEIGHT_LIMIT, data);

        log(MessageType.SET_HEIGHT_LIMIT, String.valueOf(height));
    }

    /**
     * Tells the drone to flip in specified direction. Keep in mind that drone ay not flip, for example if the battery is lower than 50% or in other unexplained circumstances.
     * <p>
     * Choose flip direction between FORWARD, LEFT, BACKWARD, RIGHT, FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT
     *
     * @param flipDirection direction to flip the drone
     * @see FlipDirection
     */

    public void sendFlipPacket(FlipDirection flipDirection) {
        byte[] data = new byte[1];
        data[0] = flipDirection.getData();
        sendPacket(MessageType.FLIP, data);

        log(MessageType.FLIP, flipDirection.toString());
    }

    /**
     * Makes the drone start throw take-off procedure. The propellers should start slightly spinning and after throwing the drone into the air it should start hovering.
     */

    public void sendThrowTakeoffPacket() {
        sendPacket(MessageType.THROW_TAKEOFF);

        log(MessageType.THROW_TAKEOFF);
    }

    /**
     * Makes the drone start palm land procedure. If you put your hand below the drone during the next 5 seconds it should land on it.
     */

    public void sendPalmLandPacket() {
        sendPacket(MessageType.PALM_LAND);

        log(MessageType.PALM_LAND);
    }

    /**
     * File size packet must be sent after receiving packet of the same type, to start receiving the photo.
     */

    public void sendFileSizePacket() {
        sendPacket(MessageType.FILE_SIZE, new byte[]{0});

        log(MessageType.FILE_SIZE, String.valueOf(0));
    }


    /**
     * File data packet must be sent after all FilePieces of the FileChunk have been received to accept them.
     *
     * @param done      true for OK, false for NOT OK
     * @param fileID    id of the file which is being transferred
     * @param filePiece transferred file piec id
     */

    public void sendFileDataPacket(boolean done, int fileID, int filePiece) {
        byte doneByte = (byte) (done ? 1 : 0);
        byte[] payload = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).put(doneByte).putShort((short) fileID).putInt(filePiece).array();
        sendPacket(MessageType.FILE_DATA, payload);

        log(MessageType.FILE_DATA, "Done: " + done + " File ID: " + fileID + " File piece: " + filePiece);
    }

    /**
     * File complete packet must be sent after the whole photo has been received.
     *
     * @param fileID   received file id
     * @param fileSize received file size
     */

    public void sendFileCompletePacket(int fileID, int fileSize) {
        byte[] payload = ByteUtils.trim(ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putShort((short) fileID).putInt(fileSize).array());
        sendPacket(MessageType.FILE_DONE, payload);

        log(MessageType.FILE_DONE, "File ID: " + fileID + " File size: " + fileSize);
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
     * @see SmartVideoMode
     */

    public void sendStartSmartVideoPacket(SmartVideoMode smartVideoMode, boolean start) {
        byte[] data = new byte[1];
        byte mode = smartVideoMode.getData();
        byte flag = (byte) (start ? 1 : 0);

        data[0] = (byte) (mode << 2 | flag);
        sendPacket(MessageType.START_SMART_VIDEO, data);

        log(MessageType.START_SMART_VIDEO, "Mode: " + smartVideoMode + " Start: " + start);
    }

    /**
     * Needs to be sent after receiving log header to start receiving logs.
     *
     * @param seqId sequence ID, passed in the received log header packet
     */

    public void sendLogHeaderAckPacket(short seqId) {
        byte[] data = ByteBuffer.allocate(3).put((byte) 0).order(ByteOrder.LITTLE_ENDIAN).putShort(seqId).array();

        sendPacket(MessageType.LOG_HEADER, data);

        log(MessageType.LOG_HEADER, "Sequence ID: " + seqId);
    }


    /**
     * Needs to be sent after receiving log config packet to accept it.
     *
     * @param s  some data from received config packet
     * @param s2 other data from received config packet
     */

    public void sendLogConfigAckPacket(short s, int s2) {
        byte[] data = ByteBuffer.allocate(7).put((byte) 0).order(ByteOrder.LITTLE_ENDIAN).putShort(s).putInt(s2).array();

        sendPacket(MessageType.LOG_CONFIG, data);

        log(MessageType.LOG_HEADER, ByteUtils.bytesToHex(data));
    }

    /**
     * Start or stop bounce mode.
     * <p>
     * The drone will automatically fly up and down within 0.5 and 1.2 meters of a flat surface below.
     *
     * @param start true to start and false to stop
     */

    public void sendBounceModePacket(boolean start) {
        byte[] data = new byte[1];
        data[0] = (byte) (start ? 0x30 : 0x31);
        sendPacket(MessageType.BOUNCE, data);

        log(MessageType.BOUNCE, String.valueOf(start));
    }

    //void sendCalibrationPacket();

    /**
     * Sets low battery threshold. Certain command are ignored below this threshold.
     *
     * @param battery low battery threshold in percents (eg. 15)
     */

    public void sendSetLowBatteryThresholdPacket(short battery) {
        byte[] data = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(battery).array();
        sendPacket(MessageType.SET_LOW_BATTERY_THRESHOLD, data);

        log(MessageType.SET_LOW_BATTERY_THRESHOLD, String.valueOf(battery));
    }

    /**
     * Sends a query for the drone height limit.
     */

    public void sendQueryHeightLimitPacket() {
        sendPacket(MessageType.QUERY_HEIGHT_LIMIT);

        log(MessageType.QUERY_HEIGHT_LIMIT);
    }

    /**
     * Sends a query for the drone low battery threshold.
     */

    public void sendQueryLowBatteryThresholdPacket() {
        sendPacket(MessageType.QUERY_LOW_BATTERY_THRESHOLD);

        log(MessageType.QUERY_LOW_BATTERY_THRESHOLD);
    }

    /**
     * Sends a query for the drone attitude limit.
     */

    public void sendQueryAttitudeLimitPacket() {
        sendPacket(MessageType.QUERY_ATTITUDE_LIMIT);

        log(MessageType.QUERY_ATTITUDE_LIMIT);
    }

    /**
     * Sets the attitude limit.
     *
     * @param attitude attitude in degrees, max 25
     */

    public void sendSetAttitudeLimitPacket(float attitude) {
        byte[] data = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(attitude).array();
        sendPacket(MessageType.SET_ATTITUDE_LIMIT, data);

        log(MessageType.SET_ATTITUDE_LIMIT, String.valueOf(attitude));
    }

    private void log(MessageType messageType, String data) {
        logger.info(String.format("Sent packet! Type: %s; Data: %s", messageType, data));
    }

    private void log(MessageType messageType) {
        logger.info(String.format("Sent packet! Type: %s", messageType));
    }
}
