package me.cubixor.telloapi.video;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.api.listeners.VideoListener;
import me.cubixor.telloapi.utils.ByteUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class VideoServer {

    private final Logger logger = LogManager.getLogger(VideoServer.class);
    private final int bufferSize = 1460;
    private final Drone tello;
    private final VideoManager videoManager;
    private boolean streamAligned;
    private DatagramSocket socket = null;
    private final Thread udpReceiverThread = new Thread(() -> {
        streamAligned = false;

        while (true) {
            try {
                DatagramPacket receivedPacket = new DatagramPacket(new byte[bufferSize], bufferSize);
                socket.receive(receivedPacket);

                byte[] data = receivedPacket.getData();


                createImg(data, receivedPacket.getLength());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private ScheduledFuture<?> videoScheduler;


    public VideoServer(VideoManager videoManager, Drone tello) {
        this.tello = tello;
        this.videoManager = videoManager;

        startVideoServer();
    }

    private void startVideoServer() {
        try {
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(6038));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startVideoScheduler() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        videoScheduler = executor.scheduleAtFixedRate(() -> {
            if (!tello.isConnected()) {
                videoScheduler.cancel(true);
                return;
            }

            tello.getPacketSender().sendStartVideoPacket();
        }, 0, videoManager.getIFrameInterval(), TimeUnit.MILLISECONDS);
    }

    private void createImg(byte[] data, int length) throws IOException {
        byte[] dataTrimmed = ByteUtils.trim(data);
        //byte[] dataNoTick = Arrays.copyOfRange(dataTrimmed, 2, dataTrimmed.length);

        int tick = ByteBuffer.wrap(dataTrimmed, 0, 2).getShort() & 0xffff;

        if (!streamAligned) {
            if (length != bufferSize) {
                streamAligned = true;
                tello.getPacketSender().sendStartVideoPacket();
            }
            logger.debug("Video data received!; NOT ALIGNED - IGNORING! Tick: " + tick + " Size: " + dataTrimmed.length + " Data: " + ByteUtils.bytesToHex(dataTrimmed));
        } else {
            for (VideoListener videoListener : videoManager.getVideoListeners()) {
                videoListener.onVideoDataReceived(data);
            }
            logger.debug("Video data received; Tick: " + tick + " Size: " + dataTrimmed.length + " Data: " + ByteUtils.bytesToHex(dataTrimmed));
        }
    }

    public Thread getUdpReceiverThread() {
        return udpReceiverThread;
    }

    public ScheduledFuture<?> getVideoScheduler() {
        return videoScheduler;
    }
}
