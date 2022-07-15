package me.cubixor.telloapi.video;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.api.VideoListener;
import me.cubixor.telloapi.utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class VideoServer {

    private final int bufferSize = 1460;
    private final Drone tello;
    private final VideoManager videoManager;
    private boolean streamAligned;
    //private PipedOutputStream pos;
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
        this.videoManager = videoManager;
        try {
            System.out.println("START VIDEO SERVER");
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(6038));
            //pos = new PipedOutputStream(videoManager.getVideoInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createImg(byte[] data, int length) throws IOException {
        byte[] dataTrimmed = Utils.trim(data);
        byte[] dataNoTick = Arrays.copyOfRange(dataTrimmed, 2, dataTrimmed.length);

        int tick = ByteBuffer.wrap(dataTrimmed, 0, 2).getShort() & 0xffff;
        int nalType = data[6] & 0x1f;


        if (!streamAligned) {
            System.out.println("NOT ALIGNED:   LEN: " + dataTrimmed.length + "   TICK: " + tick + "   NALTYPE: " + nalType + "   DATA: " + Utils.bytesToHex(dataTrimmed));

            if (length != bufferSize) {
                streamAligned = true;
                tello.getPacketSender().sendStartVideoPacket();
            }
        } else {
            //pos.write(data, 2, length - 2);
            //pos.flush();

            for (VideoListener videoListener : videoManager.getVideoListeners()) {
                videoListener.onVideoDataReceived(data);
            }

/*
            System.out.println("VIDEODATA:   LEN: " + dataTrimmed.length + "   TICK: " + tick + "   NALTYPE: " + nalType + "   DATA: " + Utils.bytesToHex(dataTrimmed));

            if (dataTrimmed.length < 20) {
                System.out.println("SPS/PPS:   LEN: " + dataTrimmed.length + "   TICK: " + tick + "   DATA:" + Utils.bytesToHex(dataTrimmed));

                if (dataNoTick.length == 13) {
                    System.out.println("SPS: " + Utils.bytesToHex(dataNoTick));
                } else if (dataNoTick.length == 8) {
                    System.out.println("PPS: " + Utils.bytesToHex(dataNoTick));
                }

                frameData = ByteBuffer.allocate(20000);
                return;
            }


            if (data[2] == 0x00 && data[3] == 0x00 && data[4] == 0x00 && data[5] == 0x01) {

                byte[] frameBytes = Utils.trim(frameData.array());

                if (frameBytes.length == 0) {
                    return;
                }

                System.out.println("FULLFRAME:   LEN:" + frameBytes.length + "   DATA: " + Utils.bytesToHex(frameBytes));


                try {
                    Picture out = Picture.create(960, 720, ColorSpace.YUV420); // Allocate output frame of max size
                    Picture real = decoder.decodeFrame(ByteBuffer.wrap(frameBytes), out.getData());
                    BufferedImage bi = AWTUtil.toBufferedImage(real); // If you prefere AWT image

                    Main.g.drawImage(bi, 10, 10, 960, 720, Main.jFrame);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                frameData = ByteBuffer.allocate(20000);
            }

            frameData.put(dataNoTick);

*/
        }


    }

    public Thread getUdpReceiverThread() {
        return udpReceiverThread;
    }
}
