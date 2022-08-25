package me.cubixor.telloapi.api;

import java.time.LocalDateTime;

public abstract class DroneState extends DroneStatus {

    /**
     * Drone speed calculated from {@link DroneStatus#getNorthSpeed()} and {@link DroneStatus#getEastSpeed()}
     * Value is in meters per second
     *
     * @return drone speed in horizontal axis
     */
    public abstract int getFlySpeed();

    /**
     * @see DroneStatusListener#onLightStrengthPacketReceive(boolean)
     */
    public abstract boolean isLightOK();

    /**
     * @see DroneStatusListener#onWifiStrengthPacketReceive(int, int)
     */
    public abstract int getWifiStrength();

    /**
     * @see DroneStatusListener#onWifiStrengthPacketReceive(int, int)
     */
    public abstract int getWifiInterference();

    public abstract String getWifiSSID();

    public abstract String getWifiPassword();

    public abstract String getWifiRegion();

    public abstract String getVersion();

    public abstract LocalDateTime getActivationTime();

    public abstract String getLoaderVersion();

    public abstract int getHeightLimit();

    public abstract int getLowBatteryThreshold();

    public abstract float getMaxAttitudeAngle();

}
