package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class MvoFeedbackPacket extends LogPacket {

    public static final int PACKET_ID = 29;
    private final int visionObservationCount;
    private final float velocityX;
    private final float velocityY;
    private final float velocityZ;
    private final float positionX;
    private final float positionY;
    private final float positionZ;
    private final float hoverPointUncertainty1;
    private final float hoverPointUncertainty2;
    private final float hoverPointUncertainty3;
    private final float hoverPointUncertainty4;
    private final float hoverPointUncertainty5;
    private final float hoverPointUncertainty6;
    private final float velocityUncertainty1;
    private final float velocityUncertainty2;
    private final float velocityUncertainty3;
    private final float velocityUncertainty4;
    private final float velocityUncertainty5;
    private final float velocityUncertainty6;
    private final float height;
    private final float heightUncertainty;
    private final int flags1;
    private final int flags2;
    private final int flags3;
    private final int flags4;

    public MvoFeedbackPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        visionObservationCount = (int) data[0];
        velocityX = (short) data[1] / 100.0f;
        velocityY = (short) data[2] / 100.0f;
        velocityZ = (short) data[3] / 100.0f;
        positionX = (float) data[4] / 100.0f;
        positionY = (float) data[5] / 100.0f;
        positionZ = (float) data[6] / 100.0f;
        hoverPointUncertainty1 = (float) data[7];
        hoverPointUncertainty2 = (float) data[8];
        hoverPointUncertainty3 = (float) data[9];
        hoverPointUncertainty4 = (float) data[10];
        hoverPointUncertainty5 = (float) data[11];
        hoverPointUncertainty6 = (float) data[12];
        velocityUncertainty1 = (float) data[13];
        velocityUncertainty2 = (float) data[14];
        velocityUncertainty3 = (float) data[15];
        velocityUncertainty4 = (float) data[16];
        velocityUncertainty5 = (float) data[17];
        velocityUncertainty6 = (float) data[18];
        height = (float) data[19];
        heightUncertainty = (float) data[20];
        flags1 = (short) data[21];
        flags2 = (short) data[22];
        flags3 = (short) data[23];
        flags4 = (short) data[24];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onMvoFeedbackPacketReceived(this);
        }
    }


    public int getVisionObservationCount() {
        return visionObservationCount;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public float getVelocityZ() {
        return velocityZ;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getPositionZ() {
        return positionZ;
    }

    public float getHoverPointUncertainty1() {
        return hoverPointUncertainty1;
    }

    public float getHoverPointUncertainty2() {
        return hoverPointUncertainty2;
    }

    public float getHoverPointUncertainty3() {
        return hoverPointUncertainty3;
    }

    public float getHoverPointUncertainty4() {
        return hoverPointUncertainty4;
    }

    public float getHoverPointUncertainty5() {
        return hoverPointUncertainty5;
    }

    public float getHoverPointUncertainty6() {
        return hoverPointUncertainty6;
    }

    public float getVelocityUncertainty1() {
        return velocityUncertainty1;
    }

    public float getVelocityUncertainty2() {
        return velocityUncertainty2;
    }

    public float getVelocityUncertainty3() {
        return velocityUncertainty3;
    }

    public float getVelocityUncertainty4() {
        return velocityUncertainty4;
    }

    public float getVelocityUncertainty5() {
        return velocityUncertainty5;
    }

    public float getVelocityUncertainty6() {
        return velocityUncertainty6;
    }

    public float getHeight() {
        return height;
    }

    public float getHeightUncertainty() {
        return heightUncertainty;
    }

    public int getFlags1() {
        return flags1;
    }

    public int getFlags2() {
        return flags2;
    }

    public int getFlags3() {
        return flags3;
    }

    public int getFlags4() {
        return flags4;
    }

    @Override
    public String toString() {
        return "MvoFeedbackPacket{" +
                "visionObservationCount=" + visionObservationCount +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", velocityZ=" + velocityZ +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", positionZ=" + positionZ +
                ", hoverPointUncertainty1=" + hoverPointUncertainty1 +
                ", hoverPointUncertainty2=" + hoverPointUncertainty2 +
                ", hoverPointUncertainty3=" + hoverPointUncertainty3 +
                ", hoverPointUncertainty4=" + hoverPointUncertainty4 +
                ", hoverPointUncertainty5=" + hoverPointUncertainty5 +
                ", hoverPointUncertainty6=" + hoverPointUncertainty6 +
                ", velocityUncertainty1=" + velocityUncertainty1 +
                ", velocityUncertainty2=" + velocityUncertainty2 +
                ", velocityUncertainty3=" + velocityUncertainty3 +
                ", velocityUncertainty4=" + velocityUncertainty4 +
                ", velocityUncertainty5=" + velocityUncertainty5 +
                ", velocityUncertainty6=" + velocityUncertainty6 +
                ", height=" + height +
                ", heightUncertainty=" + heightUncertainty +
                ", flags1=" + flags1 +
                ", flags2=" + flags2 +
                ", flags3=" + flags3 +
                ", flags4=" + flags4 +
                '}';
    }
}
