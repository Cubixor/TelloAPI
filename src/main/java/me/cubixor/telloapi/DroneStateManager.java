package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneState;

import java.time.LocalDateTime;

public class DroneStateManager extends DroneState {

    private boolean lightOK;
    private int wifiStrength;
    private int wifiInterference;

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

    public void setDroneStatus(int batteryLow, int batteryLower, int batteryPercentage, int batteryState, int cameraState, int downVisualState, int droneBatteryLeft, int droneFlyTimeLeft, int droneHover, int eMOpen, int eMSky, int eMgroud, int eastSpeed, int electricalMachineryState, int factoryMode, int flyMode, /*int flySpeed,*/ int flyTime, int frontIn, int frontLSC, int frontOut, int gravityState, int groundSpeed, int height, int imuCalibrationState, int imuState, int northSpeed, int outageRecording, int powerState, int pressureState, /*int smartVideoExitMode,*/ int temperatureHeight, int throwFlyTimer, int windState) {
        this.batteryLow = batteryLow;
        this.batteryLower = batteryLower;
        this.batteryPercentage = batteryPercentage;
        this.batteryState = batteryState;
        this.cameraState = cameraState;
        this.downVisualState = downVisualState;
        this.droneBatteryLeft = droneBatteryLeft;
        this.droneFlyTimeLeft = droneFlyTimeLeft;
        this.droneHover = droneHover;
        this.eMOpen = eMOpen;
        this.eMSky = eMSky;
        this.eMgroud = eMgroud;
        this.eastSpeed = eastSpeed;
        this.electricalMachineryState = electricalMachineryState;
        this.factoryMode = factoryMode;
        this.flyMode = flyMode;
        //this.flySpeed = flySpeed;
        this.flyTime = flyTime;
        this.frontIn = frontIn;
        this.frontLSC = frontLSC;
        this.frontOut = frontOut;
        this.gravityState = gravityState;
        this.groundSpeed = groundSpeed;
        this.height = height;
        this.imuCalibrationState = imuCalibrationState;
        this.imuState = imuState;
        this.northSpeed = northSpeed;
        this.outageRecording = outageRecording;
        this.powerState = powerState;
        this.pressureState = pressureState;
        //this.smartVideoExitMode = smartVideoExitMode;
        this.temperatureHeight = temperatureHeight;
        this.throwFlyTimer = throwFlyTimer;
        this.windState = windState;
    }

    @Override
    public int getBatteryLow() {
        return batteryLow;
    }

    @Override
    public int getBatteryLower() {
        return batteryLower;
    }

    @Override
    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    @Override
    public int getBatteryState() {
        return batteryState;
    }

    @Override
    public int getCameraState() {
        return cameraState;
    }

    @Override
    public int getDownVisualState() {
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
    public int getDroneHover() {
        return droneHover;
    }

    @Override
    public int geteMOpen() {
        return eMOpen;
    }

    @Override
    public int geteMSky() {
        return eMSky;
    }

    @Override
    public int geteMgroud() {
        return eMgroud;
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
    public int getFactoryMode() {
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
    public int getFrontIn() {
        return frontIn;
    }

    @Override
    public int getFrontLSC() {
        return frontLSC;
    }

    @Override
    public int getFrontOut() {
        return frontOut;
    }

    @Override
    public int getGravityState() {
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
    public int getImuState() {
        return imuState;
    }

    @Override
    public int getNorthSpeed() {
        return northSpeed;
    }

    @Override
    public int getOutageRecording() {
        return outageRecording;
    }

    @Override
    public int getPowerState() {
        return powerState;
    }

    @Override
    public int getPressureState() {
        return pressureState;
    }

    @Override
    public int getTemperatureHeight() {
        return temperatureHeight;
    }

    @Override
    public int getThrowFlyTimer() {
        return throwFlyTimer;
    }

    @Override
    public int getWindState() {
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
