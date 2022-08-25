package me.cubixor.telloapi;

import java.io.IOException;
import java.net.*;

public class UdpServer {

    private final PacketDecoder packetDecoder;
    private DatagramSocket socket;


    public UdpServer(Drone tello) {
        packetDecoder = new PacketDecoder(tello);

        setupSocket();
        setupReceiver();
    }

    private void setupSocket() {
        try {
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(8889));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void setupReceiver() {
        new Thread(() -> {
            int bufferSize = 2048;

            while (true) {
                try {
                    DatagramPacket receivedPacket = new DatagramPacket(new byte[bufferSize], bufferSize);
                    socket.receive(receivedPacket);

                    byte[] data = receivedPacket.getData();
                    packetDecoder.handlePacket(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void sendPacket(byte[] buf) {
        new Thread(() -> {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.10.1"), 8889);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
