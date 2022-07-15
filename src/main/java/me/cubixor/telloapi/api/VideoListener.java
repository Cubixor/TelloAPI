package me.cubixor.telloapi.api;

public interface VideoListener {


    //void onFrameReceived(Frame image);


    void onVideoDataReceived(byte[] data);
}
