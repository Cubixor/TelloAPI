package me.cubixor.telloapi.api;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacketListener;

public abstract class Tello {

    public static Tello build() {
        return new Drone(500, 2);
    }

    public abstract boolean isConnected();

    public abstract void addConnectionListener(DroneConnectionListener droneConnectionListener);

    public abstract void addDroneStatusListener(DroneStatusListener droneStatusListener);

    public abstract void addVideoListener(VideoListener videoListener);

    public abstract void addFileListener(FileReceiver fileReceiver);

    public abstract void addLogPacketListener(LogPacketListener logPacketListener);

    public abstract PacketSender getPacketSender();

    public abstract DroneState getDroneState();

    public abstract VideoInfo getVideoInfo();

    public abstract float getRoll();

    public abstract void setRoll(float roll);

    public abstract float getPitch();

    public abstract void setPitch(float pitch);

    public abstract float getThrottle();

    public abstract void setThrottle(float throttle);

    public abstract float getYaw();

    public abstract void setYaw(float yaw);

    public abstract boolean isFastMode();

    public abstract void setFastMode(boolean fastMode);

    public abstract float[] getAxis();

    public abstract void setAxis(float roll, float pitch, float throttle, float yaw);

    public abstract void startVideoStream(int iFrameInterval);

    public abstract void updateIFrameInterval(int iFrameInterval);
}
