package me.cubixor.telloapi.api;

import java.time.LocalDateTime;

public abstract class DroneState extends DroneStatus {

    public abstract boolean isLightOK();

    public abstract int getWifiStrength();

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
