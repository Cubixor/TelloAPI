package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class AirCompensateDataPacket extends LogPacket {

    public static final int PACKET_ID = 10100;

    private final float compAlti;
    private final float windSpd;
    private final float windX;
    private final float windY;
    private final float motorSpd;
    private final short velLevel;

    public AirCompensateDataPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        compAlti = (float) data[0];
        windSpd = (float) data[1];
        windX = (float) data[2];
        windY = (float) data[3];
        motorSpd = (float) data[4];
        velLevel = (short) data[5];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onAirCompensateDataPacketReceived(this);
        }
    }

    public float getCompAlti() {
        return compAlti;
    }

    public float getWindSpd() {
        return windSpd;
    }

    public float getWindX() {
        return windX;
    }

    public float getWindY() {
        return windY;
    }

    public float getMotorSpd() {
        return motorSpd;
    }

    public short getVelLevel() {
        return velLevel;
    }

    @Override
    public String toString() {
        return "AirCompensateDataPacket{" +
                "compAlti=" + compAlti +
                ", windSpd=" + windSpd +
                ", windX=" + windX +
                ", windY=" + windY +
                ", motorSpd=" + motorSpd +
                ", velLevel=" + velLevel +
                '}';
    }
}
