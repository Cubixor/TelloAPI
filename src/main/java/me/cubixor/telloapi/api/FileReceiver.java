package me.cubixor.telloapi.api;

public interface FileReceiver {

    void onPhotoReceived(byte[] data);
}
