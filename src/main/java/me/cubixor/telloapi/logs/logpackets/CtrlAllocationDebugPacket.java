package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class CtrlAllocationDebugPacket extends LogPacket {

    public static final int PACKET_ID = 1306;

    private final short rawTiltX;
    private final short rawTiltY;
    private final short rawTors;
    private final short rawLift;
    private final short fixTiltX;
    private final short fixTiltY;
    private final short fixTor;
    private final short fixLift;
    private final short boundMax;
    private final short boundMin;
    private final short limitTorsScale;


    public CtrlAllocationDebugPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        rawTiltX = (short) data[0];
        rawTiltY = (short) data[1];
        rawTors = (short) data[2];
        rawLift = (short) data[3];
        fixTiltX = (short) data[4];
        fixTiltY = (short) data[5];
        fixTor = (short) data[6];
        fixLift = (short) data[7];
        boundMax = (short) data[8];
        boundMin = (short) data[9];
        limitTorsScale = (short) data[10];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlAllocationDebugPacketReceived(this);
        }
    }

    public short getRawTiltX() {
        return rawTiltX;
    }

    public short getRawTiltY() {
        return rawTiltY;
    }

    public short getRawTors() {
        return rawTors;
    }

    public short getRawLift() {
        return rawLift;
    }

    public short getFixTiltX() {
        return fixTiltX;
    }

    public short getFixTiltY() {
        return fixTiltY;
    }

    public short getFixTor() {
        return fixTor;
    }

    public short getFixLift() {
        return fixLift;
    }

    public short getBoundMax() {
        return boundMax;
    }

    public short getBoundMin() {
        return boundMin;
    }

    public short getLimitTorsScale() {
        return limitTorsScale;
    }

    @Override
    public String toString() {
        return "CtrlAllocationDebugPacket{" +
                "rawTiltX=" + rawTiltX +
                ", rawTiltY=" + rawTiltY +
                ", rawTors=" + rawTors +
                ", rawLift=" + rawLift +
                ", fixTiltX=" + fixTiltX +
                ", fixTiltY=" + fixTiltY +
                ", fixTor=" + fixTor +
                ", fixLift=" + fixLift +
                ", boundMax=" + boundMax +
                ", boundMin=" + boundMin +
                ", limitTorsScale=" + limitTorsScale +
                '}';
    }
}
