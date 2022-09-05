package me.cubixor.telloapi.api;

public interface DroneAxis {
    float getRoll();

    void setRoll(float roll);

    float getPitch();

    void setPitch(float pitch);

    float getThrottle();

    void setThrottle(float throttle);

    float getYaw();

    void setYaw(float yaw);

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
    boolean isFastMode();

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
    void setFastMode(boolean fastMode);

    /**
     * Sets all drone axis at once
     *
     * @param roll     roll value (from -1.0 to 1.0)
     * @param pitch    pitch value (from -1.0 to 1.0)
     * @param throttle throttle value (from -1.0 to 1.0)
     * @param yaw      yaw value (from -1.0 to 1.0)
     */
    void setAxis(float roll, float pitch, float throttle, float yaw);

    /**
     * Sets all drone axis to neutral 0
     */
    void resetAxis();
}
