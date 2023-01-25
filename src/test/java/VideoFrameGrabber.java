import me.cubixor.telloapi.api.video.VideoMode;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PipedInputStream;

public class VideoFrameGrabber {

    public static final Java2DFrameConverter conv = new Java2DFrameConverter();
    public static final MyFrame jFrame = new MyFrame();
    public static final Graphics g = jFrame.getGraphics();
    private CustomFFmpegFrameGrabber frameGrabber;
    private final Thread videoThread = new Thread(() -> {
        try {
            frameGrabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
            return;
        }

        Frame frame;
        while (true) {
            try {
                frame = frameGrabber.grabImage();

                BufferedImage image = conv.convert(frame);
                g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), jFrame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


    public VideoFrameGrabber(PipedInputStream pis) {
        frameGrabber = new CustomFFmpegFrameGrabber(pis);
        frameGrabber.setImageMode(FrameGrabber.ImageMode.COLOR);
        frameGrabber.setFormat("h264");
        frameGrabber.setFrameRate(30);
        frameGrabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        videoThread.start();
    }

    public CustomFFmpegFrameGrabber getFrameGrabber() {
        return frameGrabber;
    }

    public Thread getVideoThread() {
        return videoThread;
    }

    public void applyFrameSize(VideoMode videoMode) {
        frameGrabber.setImageWidth(videoMode.getWidth());
        frameGrabber.setImageHeight(videoMode.getHeight());
    }
}
