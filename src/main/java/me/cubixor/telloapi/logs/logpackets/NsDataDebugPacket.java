package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class NsDataDebugPacket extends LogPacket {

    public static final int PACKET_ID = 10085;

    private final float d00;
    private final float d01;
    private final float d02;
    private final float d03;
    private final float d04;
    private final float d05;
    private final float d06;
    private final float d07;
    private final float d08;
    private final float d09;
    private final float d10;
    private final float d11;
    private final float d12;
    private final float d13;
    private final float d14;
    private final float d15;
    private final float d16;
    private final float d17;
    private final float d18;
    private final float d19;

    public NsDataDebugPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        d00 = (float) data[0];
        d01 = (float) data[1];
        d02 = (float) data[2];
        d03 = (float) data[3];
        d04 = (float) data[4];
        d05 = (float) data[5];
        d06 = (float) data[6];
        d07 = (float) data[7];
        d08 = (float) data[8];
        d09 = (float) data[9];
        d10 = (float) data[10];
        d11 = (float) data[11];
        d12 = (float) data[12];
        d13 = (float) data[13];
        d14 = (float) data[14];
        d15 = (float) data[15];
        d16 = (float) data[16];
        d17 = (float) data[17];
        d18 = (float) data[18];
        d19 = (float) data[19];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onNsDataDebugPacketReceived(this);
        }
    }

    public float getD00() {
        return d00;
    }

    public float getD01() {
        return d01;
    }

    public float getD02() {
        return d02;
    }

    public float getD03() {
        return d03;
    }

    public float getD04() {
        return d04;
    }

    public float getD05() {
        return d05;
    }

    public float getD06() {
        return d06;
    }

    public float getD07() {
        return d07;
    }

    public float getD08() {
        return d08;
    }

    public float getD09() {
        return d09;
    }

    public float getD10() {
        return d10;
    }

    public float getD11() {
        return d11;
    }

    public float getD12() {
        return d12;
    }

    public float getD13() {
        return d13;
    }

    public float getD14() {
        return d14;
    }

    public float getD15() {
        return d15;
    }

    public float getD16() {
        return d16;
    }

    public float getD17() {
        return d17;
    }

    public float getD18() {
        return d18;
    }

    public float getD19() {
        return d19;
    }

    @Override
    public String toString() {
        return "NsDataDebugPacket{" +
                "d00=" + d00 +
                ", d01=" + d01 +
                ", d02=" + d02 +
                ", d03=" + d03 +
                ", d04=" + d04 +
                ", d05=" + d05 +
                ", d06=" + d06 +
                ", d07=" + d07 +
                ", d08=" + d08 +
                ", d09=" + d09 +
                ", d10=" + d10 +
                ", d11=" + d11 +
                ", d12=" + d12 +
                ", d13=" + d13 +
                ", d14=" + d14 +
                ", d15=" + d15 +
                ", d16=" + d16 +
                ", d17=" + d17 +
                ", d18=" + d18 +
                ", d19=" + d19 +
                '}';
    }
}
