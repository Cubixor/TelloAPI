import me.cubixor.telloapi.api.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main implements DroneStatusListener {

    public static final MyFrame jFrame = new MyFrame();
    public static final Graphics g = jFrame.getGraphics();
    //public static final Java2DFrameConverter conv = new Java2DFrameConverter();

    public static void main(String[] args) {
        Tello tello = Tello.build();

        //tello.addDroneStatusListener(new Main());


        tello.addConnectionListener(new DroneConnectionListener() {
            @Override
            public void onConnect() {
                System.out.println("CONNECT");
                tello.startVideoStream(300);
                //tello.getPacketSender().sendTakeOffPacket();
                //tello.setFastMode(true);
                //tello.setAxis(0.2f, 0,0,0);

                ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
                Runnable r2 = () -> {
                    System.out.println("QUERYBITRATE");
                    tello.getPacketSender().sendQueryBitratePacket();
                };
                executor.scheduleAtFixedRate(r2, 0, 5, TimeUnit.SECONDS);
            }

            @Override
            public void onDisconnect() {
                System.out.println("DISCONNECT");
            }
        });


        tello.addVideoListener(new VideoListener() {
/*            @Override
            public void onFrameReceived(Frame frame) {
                BufferedImage image = conv.convert(frame);
                g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), jFrame);
            }*/

            @Override
            public void onVideoDataReceived(byte[] data) {

            }
        });

/*
        tello.addFileListener(new FileReceiver() {
            int i = 0;

            @Override
            public void onPhotoReceived(byte[] data) {
                BufferedImage image = decodeImage(data);
                g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), jFrame);
                try {
                    ImageIO.write(image, "jpg", new File("output" + i + ".jpg"));
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
*/




/*
        Runnable r3 = () -> {
            //tello.setAxis(0,0,0,0);
        };

        executor.schedule(r3, 15, TimeUnit.SECONDS);



        Runnable r4 = () -> {
            //tello.getPacketSender().sendLandPacket();
        };

        executor.schedule(r4, 15, TimeUnit.SECONDS);

*/

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

    @Override
    public void onLightStrengthPacketReceive(boolean lightOK) {
        //System.out.println("LIGHT " + lightOK);
    }

    @Override
    public void onWifiStrengthPacketReceive(int wifiStrength, int wifiInterference) {
        //System.out.println("WIFI STRENGTH " + wifiStrength + "   INTERFERENCE " + wifiInterference);
    }

    @Override
    public void onStatusPacketReceive(DroneStatus droneStatus) {

    }
}
