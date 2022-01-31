package me.cubixor.telloapi.api;

import java.time.LocalDateTime;

public interface PacketSender {

    float[] getExposureValues();

    void sendConnectPacket();

    void sendQuerySSIDPacket();

    //void sendSetSSIDPacket(String ssid);

    void sendQueryPasswordPacket();

    //void sendSetPasswordPacket(String password);

    void sendQueryRegionPacket();

    //void sendSetRegionPacket(String region);

    void sendSetBitratePacket(VideoInfo.BitRate bitrate);

    //void sendSetDynAdjRatePacket();

    void sendSetEISPacket(int i);

    void sendStartVideoPacket();

    void sendQueryBitratePacket();

    void sendTakePicturePacket();

    void sendChangeVideoAspectPacket(VideoInfo.VideoMode videoMode);

    void sendStartRecordingPacket(boolean recording);

    void sendSetExposurePacket(int exposure);

    void sendSetExposurePacket(float exposure);

    void sendJPEGQualityPacket(boolean highQuality);

    void sendQueryVersionPacket();

    void sendSetDateTimePacket(LocalDateTime localDateTime);

    void sendQueryActivationTimePacket();

    void sendQueryLoaderVersionPacket();

    void sendSticksPacket(int roll, int pitch, int throttle, int yaw, boolean fastMode);

    void sendTakeOffPacket();

    void sendLandPacket();

    void sendSetHeightLimitPacket(short height);

    void sendFlipPacket(FlipDirection flipDirection);

    void sendThrowTakeoffPacket();

    void sendPalmLandPacket();

    void sendFileSizePacket();

    void sendFileDataPacket(boolean done, int fileID, int filePiece);

    void sendFileCompletePacket(int fileID, int fileSize);

    void sendStartSmartVideoPacket(VideoInfo.SmartVideoMode smartVideoMode, boolean start);

    void sendBounceModePacket(boolean start);

    //void sendCalibrationPacket();

    void sendSetLowBatteryThresholdPacket(short battery);

    void sendQueryHeightLimitPacket();

    void sendQueryLowBatteryThresholdPacket();

    void sendQueryAttitudeLimitPacket();

    void sendSetAttitudeLimitPacket(float attitude);

    void sendStartMotorsPacket();

    void sendStopMotorsPacket();

    void sendEmergencyPacket();
}