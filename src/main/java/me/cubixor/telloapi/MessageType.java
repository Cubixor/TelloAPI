package me.cubixor.telloapi;

public enum MessageType {
    QUERY_SSID(0x0011, PacketType.GET_INFO),
    SET_SSID(0x0012, PacketType.SET_INFO),
    QUERY_PASSWORD(0x0013, PacketType.GET_INFO),
    SET_PASSWORD(0x0014, PacketType.SET_INFO),
    QUERY_REGION(0x0015, PacketType.GET_INFO),
    SET_REGION(0x0016, PacketType.SET_INFO),
    WIFI_STRENGTH(0x001A, PacketType.EXTENDED),
    SET_BITRATE(0x0020, PacketType.SET_INFO),
    SET_DYN_ADJ_RATE(0x0021, PacketType.SET_INFO),
    SET_EIS(0x0024, PacketType.SET_INFO),
    START_VIDEO(0x0025, PacketType.DATA2),
    QUERY_BITRATE(0x0028, PacketType.GET_INFO),
    TAKE_PICTURE(0x0030, PacketType.SET_INFO),
    SET_VIDEO_ASPECT(0x0031, PacketType.SET_INFO),
    START_RECORDING(0x0032, PacketType.SET_INFO),
    EXPOSURE_VALUES(0x0034, PacketType.GET_INFO),
    LIGHT_STRENGTH(0x0035, PacketType.EXTENDED),
    QUERY_JPEG_QUALITY(0x0037, PacketType.SET_INFO),
    ERROR_1(0x0043, PacketType.EXTENDED),
    ERROR_2(0x0044, PacketType.EXTENDED),
    QUERY_VERSION(0x0045, PacketType.GET_INFO),
    SET_DATE_TIME(0x0046, PacketType.DATA1),
    QUERY_ACTIVATION_TIME(0x0047, PacketType.GET_INFO),
    QUERY_LOADER_VERSION(0x0049, PacketType.GET_INFO),
    SET_STICKS(0x0050, PacketType.DATA2),
    TAKEOFF(0x0054, PacketType.SET_INFO),
    LAND(0x0055, PacketType.SET_INFO),
    STATUS(0x0056, PacketType.EXTENDED),
    SET_HEIGHT_LIMIT(0x0058, PacketType.SET_INFO),
    TELLO_CMD_HANDLE_IMU_ANGLE(0x005a, PacketType.GET_INFO),
    FLIP(0x005C, PacketType.FLIP),
    THROW_TAKEOFF(0x005D, PacketType.GET_INFO),
    PALM_LAND(0x005E, PacketType.GET_INFO),
    FILE_SIZE(0x0062, PacketType.DATA1),
    FILE_DATA(0x0063, PacketType.DATA1),
    FILE_DONE(0x0064, PacketType.GET_INFO),
    START_SMART_VIDEO(0x0080, PacketType.SET_INFO),
    SMART_VIDEO_STATUS(0x0081, PacketType.DATA1),
    LOG_HEADER(0x1050, PacketType.DATA1),
    LOG_DATA(0x1051, PacketType.EXTENDED),
    LOG_CONFIG(0x1052, PacketType.DATA1),
    BOUNCE(0x1053, PacketType.SET_INFO),
    CALIBRATION(0x1054, PacketType.SET_INFO),
    SET_LOW_BATTERY_THRESHOLD(0x1055, PacketType.SET_INFO),
    QUERY_HEIGHT_LIMIT(0x1056, PacketType.GET_INFO),
    QUERY_LOW_BATTERY_THRESHOLD(0x1057, PacketType.GET_INFO),
    SET_ATTITUDE_LIMIT(0x1058, PacketType.SET_INFO),
    QUERY_ATTITUDE_LIMIT(0x1059, PacketType.GET_INFO),
    UNKNOWN(0, 0);


    private final short messageID;
    private final byte packetType;

    MessageType(int messageID, int packetType) {
        this.messageID = (short) messageID;
        this.packetType = (byte) packetType;
    }

    public static MessageType getMessageType(short data) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getMessageID() == data) {
                return messageType;
            }
        }
        return UNKNOWN;
    }

    public short getMessageID() {
        return messageID;
    }

    public byte getPacketType() {
        return packetType;
    }
}

class PacketType {
    public static final byte EXTENDED = 0x8;
    public static final byte GET_INFO = 0x48;
    public static final byte SET_INFO = 0x68;
    public static final byte DATA1 = 0x50;
    public static final byte DATA2 = 0x60;
    public static final byte FLIP = 0x70;
}
