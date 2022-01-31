package me.cubixor.telloapi.video;

import me.cubixor.telloapi.Drone;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VideoServer {

    private final int bufferSize = 1460;
    private final Drone tello;
    private boolean streamAligned;
    private PipedOutputStream pos;
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

    public VideoServer(VideoManager videoManager, Drone tello) {
        this.tello = tello;
        try {
            socket = new DatagramSocket(6038);
            pos = new PipedOutputStream(videoManager.getVideoInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        udpReceiverThread.start();
    }

    private void createImg(byte[] data, int length) throws IOException {
        if (!streamAligned) {
            if (length != bufferSize) {
                streamAligned = true;
                tello.getPacketSender().sendStartVideoPacket();
            }
        } else {
            pos.write(data, 2, length - 2);
            pos.flush();
        }
    }

    public Thread getUdpReceiverThread() {
        return udpReceiverThread;
    }
}
