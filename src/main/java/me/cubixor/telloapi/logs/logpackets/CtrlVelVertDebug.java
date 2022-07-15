package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class CtrlVelVertDebug extends LogPacket {

    public static final int PACKET_ID = 1202;

    private final short velTag;
    private final short velCmd;
    private final short velBefore;
    private final short velAfter;
    private final short velFdbk;
    private final short gndEn;
    private final short gndWork;
    private final short roofEn;
    private final short roofWork;
    private final short lowEn;
    private final short lowWork;
    private final short highEn;
    private final short highWork;
    private final short APWork;
    private final short APEn0;
    private final short APEn1;
    private final short APEn2;
    private final short hitEn;
    private final short hitWork;
    private final short hitNorm;
    private final short hitDamp;
    private final short downEn;
    private final short downWork;
    private final short downNorm;
    private final short downDamp;

    public CtrlVelVertDebug(Drone drone, int tick, Object[] data) {
        super(tick);

        velTag = (short) data[0];
        velCmd = (short) data[1];
        velBefore = (short) data[2];
        velAfter = (short) data[3];
        velFdbk = (short) data[4];
        gndEn = (short) data[5];
        gndWork = (short) data[6];
        roofEn = (short) data[7];
        roofWork = (short) data[8];
        lowEn = (short) data[9];
        lowWork = (short) data[10];
        highEn = (short) data[11];
        highWork = (short) data[12];
        APWork = (short) data[13];
        APEn0 = (short) data[14];
        APEn1 = (short) data[15];
        APEn2 = (short) data[16];
        hitEn = (short) data[17];
        hitWork = (short) data[18];
        hitNorm = (short) data[19];
        hitDamp = (short) data[20];
        downEn = (short) data[21];
        downWork = (short) data[22];
        downNorm = (short) data[23];
        downDamp = (short) data[24];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlVelVertDebugPacketReceived(this);
        }
    }

    public short getVelTag() {
        return velTag;
    }

    public short getVelCmd() {
        return velCmd;
    }

    public short getVelBefore() {
        return velBefore;
    }

    public short getVelAfter() {
        return velAfter;
    }

    public short getVelFdbk() {
        return velFdbk;
    }

    public short getGndEn() {
        return gndEn;
    }

    public short getGndWork() {
        return gndWork;
    }

    public short getRoofEn() {
        return roofEn;
    }

    public short getRoofWork() {
        return roofWork;
    }

    public short getLowEn() {
        return lowEn;
    }

    public short getLowWork() {
        return lowWork;
    }

    public short getHighEn() {
        return highEn;
    }

    public short getHighWork() {
        return highWork;
    }

    public short getAPWork() {
        return APWork;
    }

    public short getAPEn0() {
        return APEn0;
    }

    public short getAPEn1() {
        return APEn1;
    }

    public short getAPEn2() {
        return APEn2;
    }

    public short getHitEn() {
        return hitEn;
    }

    public short getHitWork() {
        return hitWork;
    }

    public short getHitNorm() {
        return hitNorm;
    }

    public short getHitDamp() {
        return hitDamp;
    }

    public short getDownEn() {
        return downEn;
    }

    public short getDownWork() {
        return downWork;
    }

    public short getDownNorm() {
        return downNorm;
    }

    public short getDownDamp() {
        return downDamp;
    }

    @Override
    public String toString() {
        return "CtrlVelVertDebug{" +
                "velTag=" + velTag +
                ", velCmd=" + velCmd +
                ", velBefore=" + velBefore +
                ", velAfter=" + velAfter +
                ", velFdbk=" + velFdbk +
                ", gndEn=" + gndEn +
                ", gndWork=" + gndWork +
                ", roofEn=" + roofEn +
                ", roofWork=" + roofWork +
                ", lowEn=" + lowEn +
                ", lowWork=" + lowWork +
                ", highEn=" + highEn +
                ", highWork=" + highWork +
                ", APWork=" + APWork +
                ", APEn0=" + APEn0 +
                ", APEn1=" + APEn1 +
                ", APEn2=" + APEn2 +
                ", hitEn=" + hitEn +
                ", hitWork=" + hitWork +
                ", hitNorm=" + hitNorm +
                ", hitDamp=" + hitDamp +
                ", downEn=" + downEn +
                ", downWork=" + downWork +
                ", downNorm=" + downNorm +
                ", downDamp=" + downDamp +
                '}';
    }
}
