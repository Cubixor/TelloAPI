package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class CtrlVertDebug extends LogPacket {

    public static final int PACKET_ID = 1200;

    private final short vertMode;
    private final short vertState;
    private final short vertFlag;
    private final float vertPos;
    private final float vertBrkT;
    private final short nearBound;
    private final float trueHLimit;
    private final short hitGnd;
    private final short highOsF;
    private final short lowOsF;
    private final short apHighOsF;
    private final short gndOsF;
    private final short rfOsF;
    private final short vLmtOsF;
    private final short landingType;

    public CtrlVertDebug(Drone drone, int tick, Object[] data) {
        super(tick);

        vertMode = (short) data[0];
        vertState = (short) data[1];
        vertFlag = (short) data[2];
        vertPos = (float) data[3];
        vertBrkT = (float) data[4];
        nearBound = (short) data[5];
        trueHLimit = (float) data[6];
        hitGnd = (short) data[7];
        highOsF = (short) data[8];
        lowOsF = (short) data[9];
        apHighOsF = (short) data[10];
        gndOsF = (short) data[11];
        rfOsF = (short) data[12];
        vLmtOsF = (short) data[13];
        landingType = (short) data[14];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlVertDebugPacketReceived(this);
        }
    }

    public short getVertMode() {
        return vertMode;
    }

    public short getVertState() {
        return vertState;
    }

    public short getVertFlag() {
        return vertFlag;
    }

    public float getVertPos() {
        return vertPos;
    }

    public float getVertBrkT() {
        return vertBrkT;
    }

    public short getNearBound() {
        return nearBound;
    }

    public float getTrueHLimit() {
        return trueHLimit;
    }

    public short getHitGnd() {
        return hitGnd;
    }

    public short getHighOsF() {
        return highOsF;
    }

    public short getLowOsF() {
        return lowOsF;
    }

    public short getApHighOsF() {
        return apHighOsF;
    }

    public short getGndOsF() {
        return gndOsF;
    }

    public short getRfOsF() {
        return rfOsF;
    }

    public short getvLmtOsF() {
        return vLmtOsF;
    }

    public short getLandingType() {
        return landingType;
    }

    @Override
    public String toString() {
        return "CtrlVertDebug{" +
                "vertMode=" + vertMode +
                ", vertState=" + vertState +
                ", vertFlag=" + vertFlag +
                ", vertPos=" + vertPos +
                ", vertBrkT=" + vertBrkT +
                ", nearBound=" + nearBound +
                ", trueHLimit=" + trueHLimit +
                ", hitGnd=" + hitGnd +
                ", highOsF=" + highOsF +
                ", lowOsF=" + lowOsF +
                ", apHighOsF=" + apHighOsF +
                ", gndOsF=" + gndOsF +
                ", rfOsF=" + rfOsF +
                ", vLmtOsF=" + vLmtOsF +
                ", landingType=" + landingType +
                '}';
    }
}
