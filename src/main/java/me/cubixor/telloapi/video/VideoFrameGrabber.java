/*
package me.cubixor.telloapi.video;

import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.api.VideoListener;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class VideoFrameGrabber {

    private VideoManager videoManager;
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

                for (VideoListener videoListener : videoManager.getVideoListeners()) {
                    videoListener.onFrameReceived(frame);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


    public VideoFrameGrabber(VideoManager videoManager) {
        this.videoManager = videoManager;
        frameGrabber = new CustomFFmpegFrameGrabber(videoManager.getVideoInputStream());
        frameGrabber.setImageMode(FrameGrabber.ImageMode.COLOR);
        frameGrabber.setFormat("h264");
        frameGrabber.setFrameRate(30);
        frameGrabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        frameGrabber.setImageWidth(videoManager.getVideoMode().getWidth());
        frameGrabber.setImageHeight(videoManager.getVideoMode().getHeight());
        videoThread.start();
    }

    public CustomFFmpegFrameGrabber getFrameGrabber() {
        return frameGrabber;
    }

    public Thread getVideoThread() {
        return videoThread;
    }

    public void applyFrameSize(VideoInfo.VideoMode videoMode) {
        frameGrabber.setImageWidth(videoMode.getWidth());
        frameGrabber.setImageHeight(videoMode.getHeight());
    }
}
*/
