package me.cubixor.telloapi.api;

import org.bytedeco.javacv.Frame;

public interface VideoListener {

    void onFrameReceived(Frame image);
}
