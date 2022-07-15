package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class CtrlAccVertDebug extends LogPacket {

    public static final int PACKET_ID = 1203;

    private final short accTag;
    private final short accCmd;
    private final short accFdbk;
    private final short thrCmd;

    public CtrlAccVertDebug(Drone drone, int tick, Object[] data) {
        super(tick);

        accTag = (short) data[0];
        accCmd = (short) data[1];
        accFdbk = (short) data[2];
        thrCmd = (short) data[3];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlAccVertDebugPacketReceived(this);
        }
    }

    public short getAccTag() {
        return accTag;
    }

    public short getAccCmd() {
        return accCmd;
    }

    public short getAccFdbk() {
        return accFdbk;
    }

    public short getThrCmd() {
        return thrCmd;
    }

    @Override
    public String toString() {
        return "CtrlAccVertDebug{" +
                "accTag=" + accTag +
                ", accCmd=" + accCmd +
                ", accFdbk=" + accFdbk +
                ", thrCmd=" + thrCmd +
                '}';
    }
}
