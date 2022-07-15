package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class AttiMini0Packet extends LogPacket {

    public static final int PACKET_ID = 2208;

    private final float sQw0;
    private final float sQx0;
    private final float sQy0;
    private final float sQz0;
    private final float sPgz0;
    private final float sVgz0;
    private final float sAgz0;
    private final long sRsv00;
    private final long sRsv10;
    private final long sCnt0;

    public AttiMini0Packet(Drone drone, int tick, Object[] data) {
        super(tick);

        sQw0 = (float) data[0];
        sQx0 = (float) data[1];
        sQy0 = (float) data[2];
        sQz0 = (float) data[3];
        sPgz0 = (float) data[4];
        sVgz0 = (float) data[5];
        sAgz0 = (float) data[6];
        sRsv00 = (long) data[7];
        sRsv10 = (long) data[8];
        sCnt0 = (long) data[9];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onAttiMini0PacketReceived(this);
        }
    }

    public float getsQw0() {
        return sQw0;
    }

    public float getsQx0() {
        return sQx0;
    }

    public float getsQy0() {
        return sQy0;
    }

    public float getsQz0() {
        return sQz0;
    }

    public float getsPgz0() {
        return sPgz0;
    }

    public float getsVgz0() {
        return sVgz0;
    }

    public float getsAgz0() {
        return sAgz0;
    }

    public long getsRsv00() {
        return sRsv00;
    }

    public long getsRsv10() {
        return sRsv10;
    }

    public long getsCnt0() {
        return sCnt0;
    }

    @Override
    public String toString() {
        return "AttiMini0Packet{" +
                "sQw0=" + sQw0 +
                ", sQx0=" + sQx0 +
                ", sQy0=" + sQy0 +
                ", sQz0=" + sQz0 +
                ", sPgz0=" + sPgz0 +
                ", sVgz0=" + sVgz0 +
                ", sAgz0=" + sAgz0 +
                ", sRsv00=" + sRsv00 +
                ", sRsv10=" + sRsv10 +
                ", sCnt0=" + sCnt0 +
                '}';
    }
}
