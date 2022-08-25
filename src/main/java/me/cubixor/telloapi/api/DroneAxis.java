package me.cubixor.telloapi.api;

public abstract class DroneAxis {
    public abstract float getRoll();

    public abstract void setRoll(float roll);

    public abstract float getPitch();

    public abstract void setPitch(float pitch);

    public abstract float getThrottle();

    public abstract void setThrottle(float throttle);

    public abstract float getYaw();

    public abstract void setYaw(float yaw);

    /**
     * Indicates whether drone is in fast or slow mode
     * <p>
     * <p>
     * Fast (sport) mode means no image stabilization, wider viewing angle on received video, higher maximum speed and faster reaction to joystick moves
     * <p>
     * Slow (video) mode means electronic image stabilization and therefore cropped video, slower maximum speed and slower reaction to joystick moves (for video stability)
     *
     * @return true if drone is in fast (sport) mode and false if drone is in slow (video) mode
     */
    public abstract boolean isFastMode();

    /**
     * Sets if drone should be in fast or slow mode
     * <p>
     * <p>
     * Fast (sport) mode means no image stabilization, wider viewing angle on received video, higher maximum speed and faster reaction to joystick moves
     * <p>
     * Slow (video) mode means electronic image stabilization and therefore cropped video, slower maximum speed and slower reaction to joystick moves (for video stability)
     *
     * @param fastMode true for fast mode, false for slow mode
     */
    public abstract void setFastMode(boolean fastMode);

    /**
     * Sets all drone axis at once
     *
     * @param roll     roll value (from -1.0 to 1.0)
     * @param pitch    pitch value (from -1.0 to 1.0)
     * @param throttle throttle value (from -1.0 to 1.0)
     * @param yaw      yaw value (from -1.0 to 1.0)
     */
    public abstract void setAxis(float roll, float pitch, float throttle, float yaw);

    /**
     * Sets all drone axis to neutral 0
     */
    public abstract void resetAxis();
}
