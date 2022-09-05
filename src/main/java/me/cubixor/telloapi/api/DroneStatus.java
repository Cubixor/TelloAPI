package me.cubixor.telloapi.api;

public abstract class DroneStatus {

    protected boolean batteryLow;
    protected boolean batteryLower;
    protected int batteryPercentage;
    protected boolean batteryState;
    protected int cameraState;
    protected boolean downVisualState;
    protected int droneBatteryLeft;
    protected int droneFlyTimeLeft;
    protected boolean droneHover;
    protected boolean eMOpen;
    protected boolean eMSky;
    protected boolean eMGround;
    protected int eastSpeed;
    protected int electricalMachineryState;
    protected boolean factoryMode;
    protected int flyMode;
    protected int flyTime;
    protected boolean frontIn;
    protected boolean frontLSC;
    protected boolean frontOut;
    protected boolean gravityState;
    protected int groundSpeed;
    protected int height;
    protected int imuCalibrationState;
    protected boolean imuState;
    protected int northSpeed;
    protected boolean outageRecording;
    protected boolean powerState;
    protected boolean pressureState;
    protected boolean temperatureHeight;
    protected int throwFlyTimer;
    protected boolean windState;


    /**
     * Low battery warning.
     *
     * @return true when battery is below low battery threshold (probably)
     * @see DroneState#updateLowBatteryThreshold(short)
     */
    public abstract boolean isBatteryLow();

    /**
     * Critical battery level warning.
     *
     * @return true when the battery is at critical level and drone initiates auto landing.
     */
    public abstract boolean isBatteryCritical();

    /**
     * Battery percentage
     *
     * @return battery level in percents (from 0 to 100)
     */
    public abstract int getBatteryPercentage();

    /**
     * Battery error.
     *
     * @return true when a battery error was encountered.
     */
    public abstract boolean isBatteryError();

    /**
     * Camera sensor error.
     *
     * @return true when a camera sensor error was encountered.
     */
    public abstract int isCameraError();

    /**
     * Downward vision sensor error.
     * True when a downward vision sensor error was encountered.
     */
    public abstract boolean isDownwardVisionError();

    /**
     * Not used anywhere in the original app
     * Seems to be always 0
     * TODO Check if any data is sent to drone and how to read it
     *
     * @return ?
     */
    public abstract int getDroneBatteryLeft();

    /**
     * Not used anywhere in the original app
     * Data is sent, no clue how to read it (3800~4200)
     * TODO Check how to read it
     *
     * @return ?
     */
    public abstract int getDroneFlyTimeLeft();

    /**
     * Not used anywhere in the original app
     * Seems to be always 0
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isDroneHovering();

    /**
     * True if drone is on ground (but I'm not 100% sure)
     *
     * @return ?
     */
    public abstract boolean iseMOpen();

    /**
     * True if drone is flying (but I'm not 100% sure)
     *
     * @return true if drone is flying
     */
    public abstract boolean iseMSky();

    /**
     * Not used anywhere in the original app
     * Sometimes true
     * TODO Check what data represents
     *
     * @return ?
     */
    public abstract boolean iseMGround();

    /**
     * Drone speed in east axis, used to calculate total speed.
     * Value is in meters per second.
     *
     * @return drone speed in east axis
     */
    public abstract int getEastSpeed();


    /**
     * Electrical machinery state
     * All kinds of error codes
     * <p>
     * Known error id's:
     * - 5, 24, 74 = IMU errors, calibration needed, contact support
     * - 14 = Low battery, takeoff failed
     * - 21, 30, 94, 99, 100 = Keep drone stationary and level, check motor and propeller, contact support
     * - 125, 127 = Collision detected
     * - 204 = Updating, takeoff failed
     * <p>
     * If an unknown error is sent by a drone it's just displayed on the screen
     * TODO Search for other error codes
     *
     * @return error codes
     */
    public abstract int getElectricalMachineryState();

    /**
     * Not used anywhere in the original app
     * Maybe if the drone was reset to factory settings or turned on first time? (hard to check for now)
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isInFactoryMode();

    /**
     * Represents tello flight mode id.
     * <p>
     * Known fly modes:
     * - 1 = no VPS
     * - 12 = landing (or hand land?)
     * - 48 = ?
     * <p>
     * TODO Search for other fly modes
     *
     * @return fly mode id
     */
    public abstract int getFlyMode();

    /**
     * Not used anywhere in the original app
     * Fly time, seems to increase every status packet
     * TODO Check how to read the data
     *
     * @return ?
     */
    public abstract int getFlyTime();

    /**
     * Not used anywhere in the original app
     * Seems to be always false
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isFrontIn();

    /**
     * Not used anywhere in the original app
     * Seems to be always false
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isFrontLSC();

    /**
     * Not used anywhere in the original app
     * Seems to be always false
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isFrontOut();

    /**
     * Warning that gravity re-calibration suggested, sent after abnormal takeoff.
     *
     * @return true if gravity re-calibration is suggested
     */
    public abstract boolean isGravityError();

    /**
     * Not used anywhere in the original app
     * Drone speed in the vertical axis
     * Negative when flying up and positive when flying down
     * Value is in meters per second.
     *
     * @return drone speed relative to ground
     */
    public abstract int getGroundSpeed();

    /**
     * Drone height, needs to be divided by 10 to get the value in meters.
     *
     * @return drone height in meters * 10
     */
    public abstract int getHeight();

    /**
     * IMU calibration state and error id
     * <p>
     * Known error id's:
     * - 1 = "IMU is warming up. Please wait before taking off."
     * - 2 = IMU Error
     *
     * @return imu calibration state
     */
    public abstract int getImuCalibrationState();

    /**
     * Imu error (flight controller sensor error in the original app)
     *
     * @return true if imu error was encountered
     */
    public abstract boolean isImuError();

    /**
     * Drone speed in north axis, used to calculate total speed.
     * Value is in meters per second.
     *
     * @return drone speed in north axis
     */
    public abstract int getNorthSpeed();

    /**
     * Used in the original app, no clue what it is
     * TODO Check what it does
     *
     * @return ?
     */
    public abstract boolean isOutageRecording();

    /**
     * Not used anywhere in the original app
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isPowerError();

    /**
     * Not used anywhere in the original app
     * Probably a warning that pressure is inappropriate and you shouldn't fly (but idk how to check it).
     * TODO Check if any data is sent to drone and what it represents
     *
     * @return ?
     */
    public abstract boolean isPressureError();

    /**
     * Warning that drone temperature is too high, and it will overheat soon.
     *
     * @return true when the drone temperature is too high
     */
    public abstract boolean isOverheat();

    /**
     * Throw fly timer
     *
     * @return amount of time in which throw fly mode will be active
     * @see Tello#throwTakeOff()
     */
    public abstract int getThrowFlyTimer();

    /**
     * Warning that it's too windy, you shouldn't fly and vps may fail.
     *
     * @return true when it's too windy
     */
    public abstract boolean isTooWindy();

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
                ", eMgroud=" + eMGround +
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
