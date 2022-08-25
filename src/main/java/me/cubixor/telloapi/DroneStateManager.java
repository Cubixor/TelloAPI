package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneState;
import me.cubixor.telloapi.utils.ByteUtils;

import java.time.LocalDateTime;

public class DroneStateManager extends DroneState {

    private boolean lightOK;
    private int wifiStrength;
    private int wifiInterference;
    private int flySpeed;

    private String wifiSSID;
    private String wifiPassword;
    private String wifiRegion;
    private String version;
    private LocalDateTime activationTime;
    private String loaderVersion;
    private int heightLimit;
    private int lowBatteryThreshold;
    private float maxAttitudeAngle;

    @Override
    public int getFlySpeed() {
        return flySpeed;
    }

    @Override
    public boolean isLightOK() {
        return lightOK;
    }

    public void setLightOK(boolean lightOK) {
        this.lightOK = lightOK;
    }

    @Override
    public int getWifiStrength() {
        return wifiStrength;
    }

    public void setWifiStrength(int wifiStrength) {
        this.wifiStrength = wifiStrength;
    }

    @Override
    public int getWifiInterference() {
        return wifiInterference;
    }

    public void setWifiInterference(int wifiInterference) {
        this.wifiInterference = wifiInterference;
    }

    public void updateDroneStatus(byte[] payload) {
        height = ByteUtils.connectBytes(payload[0], payload[1]);
        northSpeed = ByteUtils.connectBytes(payload[2], payload[3]);
        eastSpeed = ByteUtils.connectBytes(payload[4], payload[5]);
        groundSpeed = ByteUtils.connectBytes(payload[6], payload[7]);
        flyTime = ByteUtils.connectBytes(payload[8], payload[9]);

        imuState = ((payload[10]) & 0x1) == 1;
        pressureState = ((payload[10] >> 1) & 0x1) == 1;
        downVisualState = ((payload[10] >> 2) & 0x1) == 1;
        powerState = ((payload[10] >> 3) & 0x1) == 1;
        batteryState = ((payload[10] >> 4) & 0x1) == 1;
        gravityState = ((payload[10] >> 5) & 0x1) == 1;
        windState = ((payload[10] >> 7) & 0x1) == 1;

        imuCalibrationState = payload[11];
        batteryPercentage = payload[12];
        droneBatteryLeft = ByteUtils.connectBytes(payload[13], payload[14]);
        droneFlyTimeLeft = ByteUtils.connectBytes(payload[15], payload[16]);

        eMSky = ((payload[17]) & 0x1) == 1;
        eMGround = ((payload[17] >> 1) & 0x1) == 1;
        eMOpen = ((payload[17] >> 2) & 0x1) == 1;
        droneHover = ((payload[17] >> 3) & 0x1) == 1;
        outageRecording = ((payload[17] >> 4) & 0x1) == 1;
        batteryLow = ((payload[17] >> 5) & 0x1) == 1;
        batteryLower = ((payload[17] >> 6) & 0x1) == 1;
        factoryMode = ((payload[17] >> 7) & 0x1) == 1;

        flyMode = payload[18];
        throwFlyTimer = payload[19];
        cameraState = payload[20];
        electricalMachineryState = payload[21];

        frontIn = ((payload[22]) & 0x1) == 1;
        frontOut = ((payload[22] >> 1) & 0x1) == 1;
        frontLSC = ((payload[22] >> 2) & 0x1) == 1;

        temperatureHeight = ((payload[23]) & 0x1) == 1;

        flySpeed = (int) Math.sqrt(Math.pow(this.northSpeed, 2.0d) + Math.pow(this.eastSpeed, 2.0d));
    }

    @Override
    public boolean isBatteryLow() {
        return batteryLow;
    }

    @Override
    public boolean isBatteryCritical() {
        return batteryLower;
    }

    @Override
    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    @Override
    public boolean isBatteryError() {
        return batteryState;
    }

    @Override
    public int isCameraError() {
        return cameraState;
    }

    @Override
    public boolean isDownwardVisionError() {
        return downVisualState;
    }

    @Override
    public int getDroneBatteryLeft() {
        return droneBatteryLeft;
    }

    @Override
    public int getDroneFlyTimeLeft() {
        return droneFlyTimeLeft;
    }

    @Override
    public boolean isDroneHovering() {
        return droneHover;
    }

    @Override
    public boolean iseMOpen() {
        return eMOpen;
    }

    @Override
    public boolean iseMSky() {
        return eMSky;
    }

    @Override
    public boolean iseMGround() {
        return eMGround;
    }

    @Override
    public int getEastSpeed() {
        return eastSpeed;
    }

    @Override
    public int getElectricalMachineryState() {
        return electricalMachineryState;
    }

    @Override
    public boolean isInFactoryMode() {
        return factoryMode;
    }

    @Override
    public int getFlyMode() {
        return flyMode;
    }

    @Override
    public int getFlyTime() {
        return flyTime;
    }

    @Override
    public boolean isFrontIn() {
        return frontIn;
    }

    @Override
    public boolean isFrontLSC() {
        return frontLSC;
    }

    @Override
    public boolean isFrontOut() {
        return frontOut;
    }

    @Override
    public boolean isGravityError() {
        return gravityState;
    }

    @Override
    public int getGroundSpeed() {
        return groundSpeed;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getImuCalibrationState() {
        return imuCalibrationState;
    }

    @Override
    public boolean isImuError() {
        return imuState;
    }

    @Override
    public int getNorthSpeed() {
        return northSpeed;
    }

    @Override
    public boolean isOutageRecording() {
        return outageRecording;
    }

    @Override
    public boolean isPowerError() {
        return powerState;
    }

    @Override
    public boolean isPressureError() {
        return pressureState;
    }

    @Override
    public boolean isOverheat() {
        return temperatureHeight;
    }

    @Override
    public int getThrowFlyTimer() {
        return throwFlyTimer;
    }

    @Override
    public boolean isTooWindy() {
        return windState;
    }


    @Override
    public String getWifiSSID() {
        return wifiSSID;
    }

    public void setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
    }

    @Override
    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    @Override
    public String getWifiRegion() {
        return wifiRegion;
    }

    public void setWifiRegion(String wifiRegion) {
        this.wifiRegion = wifiRegion;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public LocalDateTime getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(LocalDateTime activationTime) {
        this.activationTime = activationTime;
    }

    @Override
    public String getLoaderVersion() {
        return loaderVersion;
    }

    public void setLoaderVersion(String loaderVersion) {
        this.loaderVersion = loaderVersion;
    }

    @Override
    public int getHeightLimit() {
        return heightLimit;
    }

    public void setHeightLimit(int heightLimit) {
        this.heightLimit = heightLimit;
    }

    @Override
    public int getLowBatteryThreshold() {
        return lowBatteryThreshold;
    }

    public void setLowBatteryThreshold(int lowBatteryThreshold) {
        this.lowBatteryThreshold = lowBatteryThreshold;
    }

    @Override
    public float getMaxAttitudeAngle() {
        return maxAttitudeAngle;
    }

    public void setMaxAttitudeAngle(float attitudeAngle) {
        this.maxAttitudeAngle = attitudeAngle;
    }

}
