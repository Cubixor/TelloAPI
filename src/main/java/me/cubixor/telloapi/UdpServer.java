package me.cubixor.telloapi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UdpServer {

    private final Drone tello;

    public UdpServer(Drone tello) {
        this.tello = tello;
        Thread udpReceiverThread = new Thread(() -> {
            int bufferSize = 1048;

            while (true) {
                try {
                    DatagramPacket receivedPacket = new DatagramPacket(new byte[bufferSize], bufferSize);
                    tello.getSocket().receive(receivedPacket);

                    byte[] data = receivedPacket.getData();
                    new PacketDecoder(tello).handlePacket(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        udpReceiverThread.start();
    }

    public void sendEcho(byte[] buf) {
        new Thread(() -> {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.10.1"), 8889);
                tello.getSocket().send(packet);
                //System.out.println("SEND " + Utils.bytesToHex(buf));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
