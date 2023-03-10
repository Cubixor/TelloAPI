package me.cubixor.telloapi.api;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.api.listeners.*;
import me.cubixor.telloapi.api.video.VideoInfo;
import me.cubixor.telloapi.logs.LogPacketListener;

public abstract class Tello {

    /**
     * Create new instance of {@link Tello} class.
     *
     * @return created instance
     */
    public static Tello build() {
        return new Drone(500, 2);
    }

    /**
     * Indicates whether the drone is connected to the api.
     *
     * @return true if the drone is connected, false if it's not
     */
    public abstract boolean isConnected();

    /**
     * @see DroneConnectionListener
     */
    public abstract void addConnectionListener(DroneConnectionListener droneConnectionListener);

    /**
     * @see DroneStatusListener
     */
    public abstract void addDroneStatusListener(DroneStatusListener droneStatusListener);

    /**
     * @see VideoListener
     */
    public abstract void addVideoListener(VideoListener videoListener);

    /**
     * @see FileReceiver
     */
    public abstract void addFileListener(FileReceiver fileReceiver);

    /**
     * @see FileMonitor
     */
    public abstract void addFileMonitor(FileMonitor fileMonitor);

    /**
     * @see LogPacketListener
     */
    public abstract void addLogPacketListener(LogPacketListener logPacketListener);


    public abstract DroneState getDroneState();

    public abstract VideoInfo getVideoInfo();

    public abstract DroneAxis getDroneAxis();

    /**
     * Tell the drone to take-off. Keep in mind that drone may not take off, for example because of low battery or an error.
     */
    public abstract void takeOff();

    /**
     * Tell the drone to land. Landing may be interrupted, for example if the throttle axis is not set to neutral.
     *
     * @param bool one unknown parameter
     */
    public abstract void land(boolean bool);

    /**
     * Makes the drone start throw take-off procedure. The propellers should start slightly spinning and after throwing the drone into the air it should start hovering.
     */
    public abstract void throwTakeOff();

    /**
     * Makes the drone start palm land procedure. If you put your hand below the drone during the next 5 seconds it should land on it.
     */
    public abstract void palmLand();

    /**
     * Tells the drone to flip in specified direction. Keep in mind that drone ay not flip, for example if the battery is lower than 50% or in other unexplained circumstances.
     * <p>
     * Choose flip direction between FORWARD, LEFT, BACKWARD, RIGHT, FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT
     *
     * @param flipDirection direction to flip the drone
     * @see FlipDirection
     */
    public abstract void flip(FlipDirection flipDirection);

    /**
     * Start or stop bounce mode.
     * <p>
     * The drone will automatically fly up and down within 0.5 and 1.2 meters of a flat surface below.
     *
     * @param enable true to start and false to stop
     */
    public abstract void bounceMode(boolean enable);

    /**
     * Starts the drone motors. After the drone motors have started you can use the throttle axis to manually takeoff.
     */
    public abstract void startMotors();

    /**
     * Stops the drone motors, after they have been started using {@link Tello#startMotors()}. It won't stop motors if drone is in flight, to do this use {@link Tello#emergencyStop()}
     */
    public abstract void stopMotors();

    /**
     * Emergency packet, not available in the original app. Makes all motors immediately stop regardless of the circumstances.
     * Use with caution, as this may cause the drone to break.
     */
    public abstract void emergencyStop();
}
