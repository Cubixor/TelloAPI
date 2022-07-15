package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class NsDataComponentPacket extends LogPacket {

    public static final int PACKET_ID = 10086;

    private final long nsCmpnt;

    public NsDataComponentPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        nsCmpnt = (long) data[0];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onNsDataComponentPacketReceived(this);
        }
    }

    public long getNsCmpnt() {
        return nsCmpnt;
    }

    @Override
    public String toString() {
        return "NsDataComponentPacket{" +
                "nsCmpnt=" + nsCmpnt +
                '}';
    }
}
