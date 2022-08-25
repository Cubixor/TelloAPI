package me.cubixor.telloapi.api;

public interface VideoListener {


    /**
     * Received when video data packet is received from a drone
     * <p>
     * First two bytes is a tick
     *
     * @param data video frame data
     */
    void onVideoDataReceived(byte[] data);
}
