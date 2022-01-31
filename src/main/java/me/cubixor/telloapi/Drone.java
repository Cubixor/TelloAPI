package me.cubixor.telloapi;

import me.cubixor.telloapi.api.*;
import me.cubixor.telloapi.photo.File;
import me.cubixor.telloapi.video.VideoManager;

import java.net.DatagramSocket;
import java.net.SocketException;
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
    private final List<DroneStatusListener> droneStatusListeners = new ArrayList<>();
    private final List<FileReceiver> fileReceivers = new ArrayList<>();
    private final HashMap<Integer, File> pendingFiles = new HashMap<>();
    private final List<DroneConnectionListener> droneConnectionListener = new ArrayList<>();
    int resetAxis = -1;
    int lastMessage;
    private DatagramSocket socket;
    private float roll = 0;
    private float pitch = 0;
    private float throttle = 0;
    private float yaw = 0;
    private boolean fastMode = false;
    private boolean connected = false;

    public Drone(int reconnectMillis, int timeoutSecs) {
        try {
            socket = new DatagramSocket(8889);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        videoManager = new VideoManager(this);
        droneStateManager = new DroneStateManager();
        udpServer = new UdpServer(this);
        packetSender = new PacketConstructor(this);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(() -> {
            if (!connected) {
                getPacketSender().sendConnectPacket();
            } else {
                Thread.currentThread().interrupt();
            }
        }, 0, reconnectMillis, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(() -> {
            if (connected) {
                if (lastMessage == timeoutSecs) {
                    connected = false;
                    Thread.currentThread().interrupt();
                    for (DroneConnectionListener droneConnectionListener : droneConnectionListener) {
                        droneConnectionListener.onDisconnect();
                    }
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

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            int a1 = (int) (1024 + 660 * roll);
            int a2 = (int) (1024 + 660 * pitch);
            int a3 = (int) (1024 + 660 * throttle);
            int a4 = (int) (1024 + 660 * yaw);
            getPacketSender().sendSticksPacket(a1, a2, a3, a4, fastMode);

            if (resetAxis == 0) {
                resetAxis = -1;
                setAxis(0, 0, 0, 0);
            } else if (resetAxis > 0) {
                resetAxis--;
            }

        }, 0, 20, TimeUnit.MILLISECONDS);

        for (DroneConnectionListener droneConnectionListener : droneConnectionListener) {
            droneConnectionListener.onConnect();
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void addConnectionListener(DroneConnectionListener droneConnectionListener) {
        this.droneConnectionListener.add(droneConnectionListener);
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
    public PacketConstructor getPacketSender() {
        return packetSender;
    }

    @Override
    public DroneState getDroneState() {
        return droneStateManager;
    }

    @Override
    public VideoManager getVideoInfo() {
        return videoManager;
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

    public List<FileReceiver> getFileReceivers() {
        return fileReceivers;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public UdpServer getUdpServer() {
        return udpServer;
    }


    private void checkAxis(float axis) {
        if (axis < -1.0f || axis > 1.0f) {
            throw new IllegalArgumentException("Axis value must be between -1.0 and 1.0");
        }
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
    public float[] getAxis() {
        float[] axis = new float[4];
        axis[0] = roll;
        axis[1] = pitch;
        axis[2] = throttle;
        axis[3] = yaw;
        return axis;
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
    public void startVideoStream() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> getPacketSender().sendStartVideoPacket(), 0, 1, TimeUnit.SECONDS);
    }
}
