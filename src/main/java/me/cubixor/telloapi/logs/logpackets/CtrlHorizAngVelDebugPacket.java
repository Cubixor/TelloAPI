package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

import java.util.Arrays;

public class CtrlHorizAngVelDebugPacket extends LogPacket {

    public static final int PACKET_ID = 1305;

    private final short gyroTag;
    private final float gyroCmdX;
    private final float gyroCmdY;
    private final float gyroCmdZ;
    private final float gyroFbkX;
    private final float gyroFbkY;
    private final float gyroFbkZ;
    private final float[] smoothedAngVel = new float[3];

    public CtrlHorizAngVelDebugPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        gyroTag = (short) data[0];
        gyroCmdX = (float) data[1];
        gyroCmdY = (float) data[2];
        gyroCmdZ = (float) data[3];
        gyroFbkX = (float) data[4];
        gyroFbkY = (float) data[5];
        gyroFbkZ = (float) data[6];
        smoothedAngVel[0] = (float) data[7];
        smoothedAngVel[1] = (float) data[8];
        smoothedAngVel[2] = (float) data[9];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlHorizAngVelDebugPacketReceived(this);
        }
    }

    public short getGyroTag() {
        return gyroTag;
    }

    public float getGyroCmdX() {
        return gyroCmdX;
    }

    public float getGyroCmdY() {
        return gyroCmdY;
    }

    public float getGyroCmdZ() {
        return gyroCmdZ;
    }

    public float getGyroFbkX() {
        return gyroFbkX;
    }

    public float getGyroFbkY() {
        return gyroFbkY;
    }

    public float getGyroFbkZ() {
        return gyroFbkZ;
    }

    public float[] getSmoothedAngVel() {
        return smoothedAngVel;
    }

    @Override
    public String toString() {
        return "CtrlHorizAngVelDebugPacket{" +
                "gyroTag=" + gyroTag +
                ", gyroCmdX=" + gyroCmdX +
                ", gyroCmdY=" + gyroCmdY +
                ", gyroCmdZ=" + gyroCmdZ +
                ", gyroFbkX=" + gyroFbkX +
                ", gyroFbkY=" + gyroFbkY +
                ", gyroFbkZ=" + gyroFbkZ +
                ", smoothedAngVel=" + Arrays.toString(smoothedAngVel) +
                '}';
    }
}
