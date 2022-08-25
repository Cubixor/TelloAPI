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

    public abstract DroneAxis getDroneAxis();
}
