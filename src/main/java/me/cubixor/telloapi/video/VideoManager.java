package me.cubixor.telloapi.video;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.PacketConstructor;
import me.cubixor.telloapi.api.listeners.VideoListener;
import me.cubixor.telloapi.api.video.BitRate;
import me.cubixor.telloapi.api.video.SmartVideoMode;
import me.cubixor.telloapi.api.video.VideoInfo;
import me.cubixor.telloapi.api.video.VideoMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


public class VideoManager implements VideoInfo {

    private static final List<Float> exposureValues = new LinkedList<>(Arrays.asList(-3.0f, -2.7f, -2.3f, -2.0f, -1.7f, -1.3f, -1.0f, -0.7f, -0.3f, 0f, 0.3f, 0.7f, 1.0f, 1.3f, 1.7f, 2.0f, 2.3f, 2.7f, 3.0f));
    private final PacketConstructor packetConstructor;
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
        packetConstructor = tello.getPacketSender();
    }

    public List<VideoListener> getVideoListeners() {
        return videoListeners;
    }

    public VideoServer getVideoServer() {
        return videoServer;
    }

    @Override
    public BitRate getBitRate() {
        return bitRate;
    }

    @Override
    public void setBitRate(BitRate bitRate) {
        this.bitRate = bitRate;
        packetConstructor.sendSetBitratePacket(bitRate);
    }

    @Override
    public float getExposure() {
        return exposure;
    }

    @Override
    public void setExposure(float exposure) {
        if (!exposureValues.contains(exposure)) {
            throw new IllegalArgumentException("Invalid exposure value!");
        }

        setExposure(exposureValues.indexOf(exposure) - 9);
    }

    @Override
    public void setExposure(int exposure) {
        if (exposure < -9 || exposure > 9) {
            throw new IllegalArgumentException("Exposure must not be lower than -9 and greater than 9!");
        }

        this.exposure = exposure;
        packetConstructor.sendSetExposurePacket(exposure);
    }

    @Override
    public int getIFrameInterval() {
        return iFrameInterval;
    }

    @Override
    public void setIFrameInterval(int iFrameInterval) {
        if (iFrameInterval < 1) {
            throw new IllegalArgumentException("iFrame interval must be greater than 0!");
        }

        this.iFrameInterval = iFrameInterval;

        ScheduledFuture<?> videoScheduler = getVideoServer().getVideoScheduler();
        if (videoScheduler != null && !videoScheduler.isCancelled()) {
            videoScheduler.cancel(true);
        }
        getVideoServer().startVideoScheduler();
    }

    @Override
    public VideoMode getVideoMode() {
        return videoMode;
    }

    @Override
    public void setVideoMode(VideoMode videoMode) {
        this.videoMode = videoMode;
        packetConstructor.sendChangeVideoAspectPacket(videoMode);
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

    @Override
    public void toggleSmartVideo(SmartVideoMode smartVideoMode, boolean start) {
        packetConstructor.sendStartSmartVideoPacket(smartVideoMode, start);
    }

    @Override
    public void startVideoStream(int iFrameInterval) {
        this.iFrameInterval = iFrameInterval;

        getVideoServer().getUdpReceiverThread().start();
        getVideoServer().startVideoScheduler();
    }

    @Override
    public void takePicture() {
        packetConstructor.sendTakePicturePacket();
    }

    @Override
    public void setJPEGQuality(boolean highQuality) {
        packetConstructor.sendJPEGQualityPacket(highQuality);
    }


}
