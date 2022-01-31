package me.cubixor.telloapi.api;

public abstract class DroneStatus {

    protected int batteryLow;
    protected int batteryLower;
    protected int batteryPercentage;
    protected int batteryState;
    protected int cameraState;
    protected int downVisualState;
    protected int droneBatteryLeft;
    protected int droneFlyTimeLeft;
    protected int droneHover;
    protected int eMOpen;
    protected int eMSky;
    protected int eMgroud;
    protected int eastSpeed;
    protected int electricalMachineryState;
    protected int factoryMode;
    protected int flyMode;
    //protected int flySpeed;
    protected int flyTime;
    protected int frontIn;
    protected int frontLSC;
    protected int frontOut;
    protected int gravityState;
    protected int groundSpeed;
    protected int height;
    protected int imuCalibrationState;
    protected int imuState;
    protected int northSpeed;
    protected int outageRecording;
    protected int powerState;
    protected int pressureState;
    //protected int smartVideoExitMode;
    protected int temperatureHeight;
    protected int throwFlyTimer;
    protected int windState;


    public abstract int getBatteryLow();

    public abstract int getBatteryLower();

    public abstract int getBatteryPercentage();

    public abstract int getBatteryState();

    public abstract int getCameraState();

    public abstract int getDownVisualState();

    public abstract int getDroneBatteryLeft();

    public abstract int getDroneFlyTimeLeft();

    public abstract int getDroneHover();

    public abstract int geteMOpen();

    public abstract int geteMSky();

    public abstract int geteMgroud();

    public abstract int getEastSpeed();

    public abstract int getElectricalMachineryState();

    public abstract int getFactoryMode();

    public abstract int getFlyMode();

    public abstract int getFlyTime();

    public abstract int getFrontIn();

    public abstract int getFrontLSC();

    public abstract int getFrontOut();

    public abstract int getGravityState();

    public abstract int getGroundSpeed();

    public abstract int getHeight();

    public abstract int getImuCalibrationState();

    public abstract int getImuState();

    public abstract int getNorthSpeed();

    public abstract int getOutageRecording();

    public abstract int getPowerState();

    public abstract int getPressureState();

    public abstract int getTemperatureHeight();

    public abstract int getThrowFlyTimer();

    public abstract int getWindState();

    @Override
    public String toString() {
        return "DroneStatus{" +
                "batteryLow=" + batteryLow +
                ", batteryLower=" + batteryLower +
                ", batteryPercentage=" + batteryPercentage +
                ", batteryState=" + batteryState +
                ", cameraState=" + cameraState +
                ", downVisualState=" + downVisualState +
                ", droneBatteryLeft=" + droneBatteryLeft +
                ", droneFlyTimeLeft=" + droneFlyTimeLeft +
                ", droneHover=" + droneHover +
                ", eMOpen=" + eMOpen +
                ", eMSky=" + eMSky +
                ", eMgroud=" + eMgroud +
                ", eastSpeed=" + eastSpeed +
                ", electricalMachineryState=" + electricalMachineryState +
                ", factoryMode=" + factoryMode +
                ", flyMode=" + flyMode +
                ", flyTime=" + flyTime +
                ", frontIn=" + frontIn +
                ", frontLSC=" + frontLSC +
                ", frontOut=" + frontOut +
                ", gravityState=" + gravityState +
                ", groundSpeed=" + groundSpeed +
                ", height=" + height +
                ", imuCalibrationState=" + imuCalibrationState +
                ", imuState=" + imuState +
                ", northSpeed=" + northSpeed +
                ", outageRecording=" + outageRecording +
                ", powerState=" + powerState +
                ", pressureState=" + pressureState +
                ", temperatureHeight=" + temperatureHeight +
                ", throwFlyTimer=" + throwFlyTimer +
                ", windState=" + windState +
                '}';
    }
}
