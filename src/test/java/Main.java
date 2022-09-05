import me.cubixor.telloapi.api.Tello;
import me.cubixor.telloapi.api.listeners.DroneConnectionListener;
import me.cubixor.telloapi.api.listeners.VideoListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {

    public static PipedOutputStream pos;
    public static PipedInputStream pis;

    public static void main(String[] args) throws Exception {
        Tello tello = Tello.build();
        pis = new PipedInputStream();
        pos = new PipedOutputStream(pis);

        tello.addConnectionListener(new DroneConnectionListener() {
            @Override
            public void onConnect() {
                System.out.println("CONNECT");

                /*tello.getVideoInfo().startVideoStream(500);
                tello.getPacketSender().sendChangeVideoAspectPacket(VideoMode.VIDEO);

                tello.getDroneAxis().setFastMode(false);

                VideoFrameGrabber videoFrameGrabber = new VideoFrameGrabber(pis);
                videoFrameGrabber.applyFrameSize(tello.getVideoInfo().getVideoMode());



                ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

                Runnable r1 = () -> {
                    tello.getDroneState().updateHeightLimit((short) 22);
                };
                Runnable r2 = () -> {
                    System.out.println(tello.getDroneState().getHeightLimit());

                };


                executor.schedule(r1, 3, TimeUnit.SECONDS);
                executor.schedule(r2, 4, TimeUnit.SECONDS);
                */

            }

            @Override
            public void onDisconnect() {
                System.out.println("DISCONNECT");
            }
        });


        tello.addFileListener(data -> {
            BufferedImage image = decodeImage(data);
            //g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), jFrame);
            try {
                ImageIO.write(image, "jpg", new File("output_" + System.currentTimeMillis() + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tello.addVideoListener(new VideoListener() {
            @Override
            public void onVideoDataReceived(byte[] data) {
                try {
                    pos.write(data, 2, data.length - 2);
                    pos.flush();
                } catch (Exception ignored) {
                }
            }
        });
    }
    public static BufferedImage decodeImage(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage image = null;
        try {
            image = ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
