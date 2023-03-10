package me.cubixor.telloapi;

import me.cubixor.telloapi.api.FlipDirection;
import me.cubixor.telloapi.api.Tello;
import me.cubixor.telloapi.api.listeners.*;
import me.cubixor.telloapi.logs.LogPacketListener;
import me.cubixor.telloapi.photo.File;
import me.cubixor.telloapi.video.VideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Drone extends Tello {

    private final Logger logger = Logger.getLogger(PacketDecoder.class.getName());
    private final UdpServer udpServer;
    private final VideoManager videoManager;
    private final PacketConstructor packetSender;
    private final DroneStateManager droneStateManager;
    private final DroneAxisManager droneAxisManager;
    private final List<DroneStatusListener> droneStatusListeners = new ArrayList<>();
    private final List<FileReceiver> fileReceivers = new ArrayList<>();
    private final List<FileMonitor> fileMonitors = new ArrayList<>();
    private final HashMap<Integer, File> pendingFiles = new HashMap<>();
    private final List<DroneConnectionListener> droneConnectionListeners = new ArrayList<>();
    private final List<LogPacketListener> logPacketListeners = new ArrayList<>();
    private int lastMessage;
    private boolean connected = false;


    public Drone(int reconnectMillis, int timeoutSecs) {

        udpServer = new UdpServer(this);
        packetSender = new PacketConstructor(this);
        videoManager = new VideoManager(this);
        droneAxisManager = new DroneAxisManager(this);
        droneStateManager = new DroneStateManager(this);

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

        logger.info("Connected to tello!");
    }

    public void onDisconnect() {
        connected = false;

        for (DroneConnectionListener droneConnectionListener : droneConnectionListeners) {
            droneConnectionListener.onDisconnect();
        }

        logger.info("Disconnected from tello!");
    }

    public HashMap<Integer, File> getPendingFiles() {
        return pendingFiles;
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

    public List<FileMonitor> getFileMonitors() {
        return fileMonitors;
    }

    public UdpServer getUdpServer() {
        return udpServer;
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
    public void addFileMonitor(FileMonitor fileMonitor) {
        fileMonitors.add(fileMonitor);
    }

    @Override
    public void addLogPacketListener(LogPacketListener logPacketListener) {
        logPacketListeners.add(logPacketListener);
    }

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

    @Override
    public void takeOff() {
        getPacketSender().sendTakeOffPacket();
    }

    @Override
    public void land(boolean bool) {
        getPacketSender().sendLandPacket(bool);
    }

    @Override
    public void throwTakeOff() {
        getPacketSender().sendThrowTakeoffPacket();
    }

    @Override
    public void palmLand() {
        getPacketSender().sendPalmLandPacket();
    }

    @Override
    public void flip(FlipDirection flipDirection) {
        getPacketSender().sendFlipPacket(flipDirection);
    }

    @Override
    public void bounceMode(boolean enable) {
        getPacketSender().sendBounceModePacket(enable);
    }

    @Override
    public void emergencyStop() {
        getPacketSender().sendEmergencyPacket();
    }

    @Override
    public void startMotors() {
        getDroneAxis().resetLater();
        getDroneAxis().setAxis(-1, -1, -1, 1);

        logger.info("Beginning starting motors procedure - axis set to -1, -1, -1, 1");
    }

    @Override
    public void stopMotors() {
        getDroneAxis().resetLater();
        getDroneAxis().setAxis(0, 0, -1, 0);

        logger.info("Beginning stopping motors procedure - axis set to 0, 0, -1, 0");
    }


}
