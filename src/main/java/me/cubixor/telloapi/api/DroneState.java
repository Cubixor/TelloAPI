package me.cubixor.telloapi.api;

import me.cubixor.telloapi.api.listeners.DroneStatusListener;

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

    /**
     * Drone firmware version string. It can be updated via official app.
     *
     * @return firmware version
     */
    public abstract String getVersion();

    /**
     * Drone activation time
     *
     * @return activation time presented as {@link LocalDateTime}
     */
    public abstract LocalDateTime getActivationTime();


    /**
     * Drone loader version string. It cannot be updated since the drone was produced.
     *
     * @return loader version
     */
    public abstract String getLoaderVersion();

    /**
     * Get the drone Wi-Fi SSID (name)
     *
     * @return ssid
     */
    public abstract String getWifiSSID();

    /**
     * Updates Wi-Fi SSID, changes visible after restart
     *
     * @param ssid new Wi-Fi SSID
     */
    public abstract void updateWifiSSID(String ssid);

    /**
     * Get the drone Wi-Fi password
     *
     * @return password
     */
    public abstract String getWifiPassword();

    /**
     * Updates Wi-Fi password, changes visible after restart
     *
     * @param password new Wi-Fi password
     */
    public abstract void updateWifiPassword(String password);

    /**
     * Get the drone Wi-Fi region
     *
     * @return region
     */
    public abstract String getWifiRegion();

    /**
     * Updates Wi-Fi region, changes visible after restart
     *
     * @param region new Wi-Fi region
     */
    public abstract void updateWifiRegion(String region);

    /**
     * Get drone height limit (how high it can fly from the ground).
     *
     * @return height limit in meters
     */
    public abstract int getHeightLimit();

    /**
     * Updates drone height limit. Height limit must not be greater than 30.
     *
     * @param height new height limit in meters
     */
    public abstract void updateHeightLimit(short height);

    /**
     * Get drone low battery threshold. Certain command are ignored below this threshold.
     *
     * @return low battery threshold in percents (eg. 15)
     */
    public abstract int getLowBatteryThreshold();

    /**
     * Updates low battery threshold. Certain command are ignored below this threshold.
     *
     * @param battery low battery threshold in percents (eg. 15)
     */
    public abstract void updateLowBatteryThreshold(short battery);

    /**
     * Get drone max attitude angle (how much the drone can tilt while flying).
     *
     * @return max attitude angle in degrees (eg. 25.0)
     */
    public abstract float getMaxAttitudeAngle();

    /**
     * Updates drone attitude limit (how much the drone can tilt while flying).
     *
     * @param angle max attitude angle in degrees (eg. 25.0)
     */
    public abstract void updateMaxAttitudeAnge(float angle);

}
