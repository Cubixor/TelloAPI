package me.cubixor.telloapi.video;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.api.VideoListener;

import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.List;


public class VideoManager extends VideoInfo {

    private final PipedInputStream videoInputStream = new PipedInputStream();
    private final List<VideoListener> videoListeners = new ArrayList<>();
    private final VideoServer videoServer;
    private final VideoFrameGrabber videoFrameGrabber;
    private BitRate bitRate;
    private float exposure;
    private VideoMode videoMode = VideoMode.PHOTO;
    private SmartVideoMode smartVideoMode;
    private boolean smartVideoRunning;

    public VideoManager(Drone tello) {
        videoServer = new VideoServer(this, tello);
        videoFrameGrabber = new VideoFrameGrabber(this);
    }

    @Override
    public BitRate getBitRate() {
        return bitRate;
    }

    public void setBitRate(BitRate bitRate) {
        this.bitRate = bitRate;
    }

    @Override
    public float getExposure() {
        return exposure;
    }

    public void setExposure(float exposure) {
        this.exposure = exposure;
    }

    @Override
    public VideoMode getVideoMode() {
        return videoMode;
    }

    public void setVideoMode(VideoMode videoMode) {
        this.videoMode = videoMode;
    }

    @Override
    public SmartVideoMode getSmartVideoMode() {
        return smartVideoMode;
    }

    public void setSmartVideoMode(SmartVideoMode smartVideoMode) {
        this.smartVideoMode = smartVideoMode;
    }

    @Override
    public boolean isSmartVideoRunning() {
        return smartVideoRunning;
    }

    public void setSmartVideoRunning(boolean smartVideoRunning) {
        this.smartVideoRunning = smartVideoRunning;
    }

    public PipedInputStream getVideoInputStream() {
        return videoInputStream;
    }

    public List<VideoListener> getVideoListeners() {
        return videoListeners;
    }

    public VideoServer getVideoServer() {
        return videoServer;
    }

    public VideoFrameGrabber getVideoFrameGrabber() {
        return videoFrameGrabber;
    }
}
