package me.cubixor.telloapi.api.listeners;

import me.cubixor.telloapi.api.video.VideoInfo;

public interface FileReceiver {

    /**
     * Called after a request to take a photo was sent to a drone and full photo was received and processed by API.
     * Won't be called if the whole file didn't arrive for some reason.
     * <p>
     * Can be for example converted to {@link java.awt.image.BufferedImage} using {@link javax.imageio.ImageIO}
     * <pre>
     * {@code
     *         ByteArrayInputStream bis = new ByteArrayInputStream(data);
     *         BufferedImage image = ImageIO.read(bis);
     * } </pre>
     *
     * @param data photo data combined from all the file chunks sent by a drone,
     *             already arranged, doesn't need further processing
     * @see VideoInfo#takePicture() Send take picture packet
     */
    void onPhotoReceived(byte[] data);
}
