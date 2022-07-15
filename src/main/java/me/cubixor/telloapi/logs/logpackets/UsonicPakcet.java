package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class UsonicPakcet extends LogPacket {

    public static final int PACKET_ID = 16;

    private final float usonicH;
    private final short usonicFlag;
    private final short usonicCnt;

    public UsonicPakcet(Drone drone, int tick, Object[] data) {
        super(tick);

        usonicH = ((short) data[0]) / 1000.0f;
        usonicFlag = (short) data[1];
        usonicCnt = (short) data[2];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onUsonciPacketReceived(this);
        }
    }

    public float getUsonicH() {
        return usonicH;
    }

    public short getUsonicFlag() {
        return usonicFlag;
    }

    public short getUsonicCnt() {
        return usonicCnt;
    }

    @Override
    public String toString() {
        return "UsonicPakcet{" +
                "usonic_h=" + usonicH +
                ", usonic_flag=" + usonicFlag +
                ", usonic_cnt=" + usonicCnt +
                '}';
    }
}
