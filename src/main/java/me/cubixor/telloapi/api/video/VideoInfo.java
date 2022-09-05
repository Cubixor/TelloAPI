package me.cubixor.telloapi.api.video;

public interface VideoInfo {

    /**
     * Get availalbe exposure values to use while executing {@link VideoInfo#setExposure(float)}
     *
     * @return exposure values
     */
    static float[] getExposureValues() {
        return new float[]{-3.0f, -2.7f, -2.3f, -2.0f, -1.7f, -1.3f, -1.0f, -0.7f, -0.3f, 0f, 0.3f, 0.7f, 1.0f, 1.3f, 1.7f, 2.0f, 2.3f, 2.7f, 3.0f};
    }

    /**
     * Get video bitrate. By default, it's set to AUTO.
     *
     * @return video bitrate
     */
    BitRate getBitRate();

    /**
     * Set video bitrate. Higher bitrate means better video quality, but if the signal is poor the video may lag.
     *
     * @param bitRate new video bitrate
     * @see BitRate
     */
    void setBitRate(BitRate bitRate);

    /**
     * Get current video exposure. 0 means automatic exposure.
     *
     * @return video exposure
     */
    float getExposure();

    /**
     * Set video exposure. Use 0 to automatically adjust exposure.
     *
     * @param exposure new video exposure
     * @see VideoInfo#getExposureValues() See available exposure values
     */
    void setExposure(float exposure);

    /**
     * Set video exposure. Use number between -9 and 9, corresponding to the exposure value from the exposure values list. Use 0 to automatically adjust exposure.
     *
     * @param exposure new video exposure
     * @see VideoInfo#getExposureValues() See available exposure values
     */
    void setExposure(int exposure);

    /**
     * Get how often an iFrame is requested from a drone.
     *
     * @return iFrame interval in milliseconds
     */
    int getIFrameInterval();

    /**
     * Set how often an iFrame is requested from a drone. If your video is glitching reducing iFrame interval may help.
     *
     * @param iFrameInterval iFrame interval in milliseconds
     */
    void setIFrameInterval(int iFrameInterval);

    /**
     * Get current video mode. In photo mode, received video is in 960x720 resolution and the drone is able to take pictures. In video mode, received video is in 1280x720 resolution.
     *
     * @return current video mode
     * @see VideoMode
     */
    VideoMode getVideoMode();

    /**
     * Set video mode. Use photo mode to receive video in 960x720 resolution and be able to take photos. Use video mode to receive video in 1280x720 resolution.
     *
     * @param videoMode choose between PHOTO and VIDEO
     * @see VideoMode
     */
    void setVideoMode(VideoMode videoMode);

    /**
     * Get currently used smart video mode.
     *
     * @return currently used smart video mode - VIDEO_360, CIRCLE or UP_AND_OUT
     * @see SmartVideoMode
     */
    SmartVideoMode getSmartVideoMode();

    /**
     * Check if any of the smart video modes is currently active.
     *
     * @return true if smart video mode is active, false if it isn't
     * @see SmartVideoMode
     */
    boolean isSmartVideoRunning();

    /**
     * Start or stop smart video mode. Available video modes:
     * <p>
     * VIDEO_360 - drone will spin 360 degrees in place
     * <p>
     * CIRCLE - drone will fly in a circle shape
     * <p>
     * UP_AND_OUT - drone will fly upwards and backwards     * @param smartVideoMode
     *
     * @param smartVideoMode VIDEO_360, CIRCLE or UP_AND_OUT
     * @param start          true to start, false to stop
     * @see SmartVideoMode
     */
    void toggleSmartVideo(SmartVideoMode smartVideoMode, boolean start);

    /**
     * Start receiving video stream from the drone.
     *
     * @param iFrameInterval how often an iFrame will be requested from the drone
     * @see VideoInfo#getIFrameInterval()
     * @see VideoInfo#setIFrameInterval(int)
     */
    void startVideoStream(int iFrameInterval);

    /**
     * Takes a picture which is then transferred to the api as a {@link me.cubixor.telloapi.photo.File}
     * <p>
     * Works only when {@link VideoMode} is set to PHOTO. May cause a lag when sending the photo.
     */
    void takePicture();

    /**
     * Sets taken picture quality. Higher quality means more detailed pictures but more time to transfer them from a drone to the api.
     *
     * @param highQuality true for higher quality and false for lower quality
     */
    void setJPEGQuality(boolean highQuality);

}
