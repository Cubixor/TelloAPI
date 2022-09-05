package me.cubixor.telloapi;

import me.cubixor.telloapi.api.DroneAxis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DroneAxisManager implements DroneAxis {

    private final Logger logger = LogManager.getLogger(DroneAxisManager.class);
    private final Drone tello;
    private float roll = 0;
    private float pitch = 0;
    private float throttle = 0;
    private float yaw = 0;
    private boolean fastMode = false;
    private int resetTimeout = -1;


    public DroneAxisManager(Drone tello) {
        this.tello = tello;
        scheduleAxisSender();
    }

    private void scheduleAxisSender() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (!tello.isConnected()) {
                return;
            }

            tello.getPacketSender().sendSticksPacket(roll, pitch, throttle, yaw, fastMode);

            if (resetTimeout == 0) {
                resetTimeout = -1;
                setAxis(0, 0, 0, 0);

                logger.info("Completing starting/stopping motors procedure - axis reset");
            } else if (resetTimeout > 0) {
                resetTimeout--;
            }

        }, 0, 20, TimeUnit.MILLISECONDS);
    }


    @Override
    public float getRoll() {
        return roll;
    }

    @Override
    public void setRoll(float roll) {
        this.roll = roll;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public float getThrottle() {
        return throttle;
    }

    @Override
    public void setThrottle(float throttle) {
        this.throttle = throttle;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public boolean isFastMode() {
        return fastMode;
    }

    @Override
    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    @Override
    public void setAxis(float roll, float pitch, float throttle, float yaw) {
        checkAxis(roll);
        checkAxis(pitch);
        checkAxis(yaw);
        checkAxis(throttle);

        this.roll = roll;
        this.pitch = pitch;
        this.throttle = throttle;
        this.yaw = yaw;
    }

    @Override
    public void resetAxis() {
        roll = 0;
        pitch = 0;
        throttle = 0;
        yaw = 0;
    }

    private void checkAxis(float axis) {
        if (axis < -1.0f || axis > 1.0f) {
            throw new IllegalArgumentException("Axis value must be between -1.0 and 1.0");
        }
    }

    public void resetLater() {
        resetTimeout = 20;
    }
}
