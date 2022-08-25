package me.cubixor.telloapi.video;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.api.VideoInfo;
import me.cubixor.telloapi.api.VideoListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


public class VideoManager extends VideoInfo {

    private final List<VideoListener> videoListeners = new ArrayList<>();
    private final VideoServer videoServer;
    private BitRate bitRate;
    private float exposure;

    private int iFrameInterval;
    private VideoMode videoMode = VideoMode.PHOTO;
    private SmartVideoMode smartVideoMode;
    private boolean smartVideoRunning;

    public VideoManager(Drone tello) {
        videoServer = new VideoServer(this, tello);
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
    public int getIFrameInterval() {
        return iFrameInterval;
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


    public List<VideoListener> getVideoListeners() {
        return videoListeners;
    }

    public VideoServer getVideoServer() {
        return videoServer;
    }

    @Override
    public void startVideoStream(int iFrameInterval) {
        this.iFrameInterval = iFrameInterval;

        getVideoServer().getUdpReceiverThread().start();
        getVideoServer().startVideoScheduler();
    }


    @Override
    public void updateIFrameInterval(int iFrameInterval) {
        this.iFrameInterval = iFrameInterval;

        ScheduledFuture<?> videoScheduler = getVideoServer().getVideoScheduler();
        if (videoScheduler != null && !videoScheduler.isCancelled()) {
            videoScheduler.cancel(true);
        }
        getVideoServer().startVideoScheduler();
    }


}
