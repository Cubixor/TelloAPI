package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class CtrlHorizAttiDebugPacket extends LogPacket {

    public static final int PACKET_ID = 1304;

    private final short attiTag;
    private final short torsType;
    private final float tgtTors;
    private final float tgtTiltX;
    private final float tgtTiltY;
    private final float tgtBodyX;
    private final float tgtBodyY;
    private final float curTors;
    private final float curTiltX;
    private final float curTiltY;

    public CtrlHorizAttiDebugPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        attiTag = (short) data[0];
        torsType = (short) data[1];
        tgtTors = (float) data[2];
        tgtTiltX = (float) data[3];
        tgtTiltY = (float) data[4];
        tgtBodyX = (float) data[5];
        tgtBodyY = (float) data[6];
        curTors = (float) data[7];
        curTiltX = (float) data[8];
        curTiltY = (float) data[9];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlHorizAttiDebugPacketReceived(this);
        }
    }

    public short getAttiTag() {
        return attiTag;
    }

    public short getTorsType() {
        return torsType;
    }

    public float getTgtTors() {
        return tgtTors;
    }

    public float getTgtTiltX() {
        return tgtTiltX;
    }

    public float getTgtTiltY() {
        return tgtTiltY;
    }

    public float getTgtBodyX() {
        return tgtBodyX;
    }

    public float getTgtBodyY() {
        return tgtBodyY;
    }

    public float getCurTors() {
        return curTors;
    }

    public float getCurTiltX() {
        return curTiltX;
    }

    public float getCurTiltY() {
        return curTiltY;
    }

    @Override
    public String toString() {
        return "CtrlHorizAttiDebugPacket{" +
                "attiTag=" + attiTag +
                ", torsType=" + torsType +
                ", tgtTors=" + tgtTors +
                ", tgtTiltX=" + tgtTiltX +
                ", tgtTiltY=" + tgtTiltY +
                ", tgtBodyX=" + tgtBodyX +
                ", tgtBodyY=" + tgtBodyY +
                ", curTors=" + curTors +
                ", curTiltX=" + curTiltX +
                ", curTiltY=" + curTiltY +
                '}';
    }
}