package me.cubixor.telloapi;

import me.cubixor.telloapi.api.*;
import me.cubixor.telloapi.logs.LogPacketListener;
import me.cubixor.telloapi.photo.File;
import me.cubixor.telloapi.video.VideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Drone extends Tello {

    private final UdpServer udpServer;
    private final VideoManager videoManager;
    private final PacketConstructor packetSender;
    private final DroneStateManager droneStateManager;
    private final DroneAxisManager droneAxisManager;
    private final List<DroneStatusListener> droneStatusListeners = new ArrayList<>();
    private final List<FileReceiver> fileReceivers = new ArrayList<>();
    private final HashMap<Integer, File> pendingFiles = new HashMap<>();
    private final List<DroneConnectionListener> droneConnectionListeners = new ArrayList<>();
    private final List<LogPacketListener> logPacketListeners = new ArrayList<>();
    private int lastMessage;
    private boolean connected = false;


    public Drone(int reconnectMillis, int timeoutSecs) {

        udpServer = new UdpServer(this);
        videoManager = new VideoManager(this);
        droneStateManager = new DroneStateManager();
        droneAxisManager = new DroneAxisManager(this);
        packetSender = new PacketConstructor(this);

        startConnectionThreads(reconnectMillis, timeoutSecs);
    }

    private void startConnectionThreads(int reconnectMillis, int timeoutSecs) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        executor.scheduleAtFixedRate(() -> {
            if (!isConnected()) {
                getPacketSender().sendConnectPacket();
            }
        }, 0, reconnectMillis, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(() -> {
            if (isConnected()) {
                if (lastMessage == timeoutSecs) {
                    onDisconnect();
                } else {
                    lastMessage++;
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    public void resetTimeout() {
        lastMessage = 0;
    }

    public void onConnect() {
        connected = true;

        getPacketSender().sendQuerySSIDPacket();
        getPacketSender().sendQueryPasswordPacket();
        getPacketSender().sendQueryRegionPacket();
        getPacketSender().sendQueryVersionPacket();
        getPacketSender().sendQueryLoaderVersionPacket();
        getPacketSender().sendQueryActivationTimePacket();

        getPacketSender().sendQueryHeightLimitPacket();
        getPacketSender().sendQueryLowBatteryThresholdPacket();
        getPacketSender().sendQueryAttitudeLimitPacket();


        for (DroneConnectionListener droneConnectionListener : droneConnectionListeners) {
            droneConnectionListener.onConnect();
        }
    }

    public void onDisconnect() {
        connected = false;

        for (DroneConnectionListener droneConnectionListener : droneConnectionListeners) {
            droneConnectionListener.onDisconnect();
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void addConnectionListener(DroneConnectionListener droneConnectionListener) {
        this.droneConnectionListeners.add(droneConnectionListener);
    }

    @Override
    public void addDroneStatusListener(DroneStatusListener droneStatusListener) {
        droneStatusListeners.add(droneStatusListener);
    }

    @Override
    public void addVideoListener(VideoListener videoListener) {
        videoManager.getVideoListeners().add(videoListener);
    }

    @Override
    public void addFileListener(FileReceiver fileReceiver) {
        fileReceivers.add(fileReceiver);
    }

    @Override
    public void addLogPacketListener(LogPacketListener logPacketListener) {
        logPacketListeners.add(logPacketListener);
    }

    @Override
    public PacketConstructor getPacketSender() {
        return packetSender;
    }

    @Override
    public DroneStateManager getDroneState() {
        return droneStateManager;
    }

    @Override
    public VideoManager getVideoInfo() {
        return videoManager;
    }

    @Override
    public DroneAxisManager getDroneAxis() {
        return droneAxisManager;
    }

    public HashMap<Integer, File> getPendingFiles() {
        return pendingFiles;
    }

    public DroneStateManager getDroneStateManager() {
        return droneStateManager;
    }

    public List<DroneStatusListener> getPacketReceivers() {
        return droneStatusListeners;
    }

    public List<LogPacketListener> getLogPacketListeners() {
        return logPacketListeners;
    }

    public List<FileReceiver> getFileReceivers() {
        return fileReceivers;
    }

    public UdpServer getUdpServer() {
        return udpServer;
    }


}
