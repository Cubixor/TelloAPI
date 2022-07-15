package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class ImuEx0Packet extends LogPacket {

    public static final int PACKET_ID = 2064;

    private final float voVx0;
    private final float voVy0;
    private final float voVz0;
    private final float voPx0;
    private final float voPy0;
    private final float voPz0;
    private final float usV0;
    private final float usP0;
    private final double rtkLong0;
    private final double rtkLati0;
    private final float rtkAlti0;
    private final int flagNavi0;
    private final int flagErr0;
    private final int flagRsv0;
    private final int exCnt0;

    public ImuEx0Packet(Drone drone, int tick, Object[] data) {
        super(tick);

        voVx0 = (float) data[0];
        voVy0 = (float) data[1];
        voVz0 = (float) data[2];
        voPx0 = (float) data[3];
        voPy0 = (float) data[4];
        voPz0 = (float) data[5];
        usV0 = (float) data[6];
        usP0 = (float) data[7];
        rtkLong0 = (double) data[8];
        rtkLati0 = (double) data[9];
        rtkAlti0 = (float) data[10];
        flagNavi0 = (int) data[11];
        flagErr0 = (int) data[12];
        flagRsv0 = (int) data[13];
        exCnt0 = (int) data[14];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onImuEx0PacketReceived(this);
        }
    }

    public float getVoVx0() {
        return voVx0;
    }

    public float getVoVy0() {
        return voVy0;
    }

    public float getVoVz0() {
        return voVz0;
    }

    public float getVoPx0() {
        return voPx0;
    }

    public float getVoPy0() {
        return voPy0;
    }

    public float getVoPz0() {
        return voPz0;
    }

    public float getUsV0() {
        return usV0;
    }

    public float getUsP0() {
        return usP0;
    }

    public double getRtkLong0() {
        return rtkLong0;
    }

    public double getRtkLati0() {
        return rtkLati0;
    }

    public float getRtkAlti0() {
        return rtkAlti0;
    }

    public int getFlagNavi0() {
        return flagNavi0;
    }

    public int getFlagErr0() {
        return flagErr0;
    }

    public int getFlagRsv0() {
        return flagRsv0;
    }

    public int getExCnt0() {
        return exCnt0;
    }

    @Override
    public String toString() {
        return "ImuEx0Packet{" +
                "voVx0=" + voVx0 +
                ", voVy0=" + voVy0 +
                ", voVz0=" + voVz0 +
                ", voPx0=" + voPx0 +
                ", voPy0=" + voPy0 +
                ", voPz0=" + voPz0 +
                ", usV0=" + usV0 +
                ", usP0=" + usP0 +
                ", rtkLong0=" + rtkLong0 +
                ", rtkLati0=" + rtkLati0 +
                ", rtkAlti0=" + rtkAlti0 +
                ", flagNavi0=" + flagNavi0 +
                ", flagErr0=" + flagErr0 +
                ", flagRsv0=" + flagRsv0 +
                ", exCnt0=" + exCnt0 +
                '}';
    }
}
