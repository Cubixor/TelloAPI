package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class ControllerPacket extends LogPacket {

    public static final int PACKET_ID = 1000;

    private final long ctrlTick;
    private final float ctrlPitch;
    private final float ctrlRoll;
    private final float ctrlYaw;
    private final float ctrlThrottle;
    private final short ctrlMode;
    private final short modeSwitch;
    private final short motorState;
    private final short sigLevel;
    private final short ctrlLevelLevel;
    private final short simModel;
    private final int maxHeight;
    private final int maxRadius;
    private final float D2Hx;
    private final float D2Hy;
    private final short actReqId;
    private final short actActId;
    private final short cmdMod;
    private final short modReqId;
    private final short isSoaringUp;
    private final short eagleTempLevel;

    public ControllerPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        ctrlTick = (long) data[0];
        ctrlPitch = (float) data[1];
        ctrlRoll = (float) data[2];
        ctrlYaw = (float) data[3];
        ctrlThrottle = (float) data[4];
        ctrlMode = (short) data[5];
        modeSwitch = (short) data[6];
        motorState = (short) data[7];
        sigLevel = (short) data[8];
        ctrlLevelLevel = (short) data[9];
        simModel = (short) data[10];
        maxHeight = (int) data[11];
        maxRadius = (int) data[12];
        D2Hx = (float) data[13];
        D2Hy = (float) data[14];
        actReqId = (short) data[15];
        actActId = (short) data[16];
        cmdMod = (short) data[17];
        modReqId = (short) data[18];
        isSoaringUp = (short) data[19];
        eagleTempLevel = (short) data[20];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onControllerPacketReceived(this);
        }
    }

    public long getCtrlTick() {
        return ctrlTick;
    }

    public float getCtrlPitch() {
        return ctrlPitch;
    }

    public float getCtrlRoll() {
        return ctrlRoll;
    }

    public float getCtrlYaw() {
        return ctrlYaw;
    }

    public float getCtrlThrottle() {
        return ctrlThrottle;
    }

    public short getCtrlMode() {
        return ctrlMode;
    }

    public short getModeSwitch() {
        return modeSwitch;
    }

    public short getMotorState() {
        return motorState;
    }

    public short getSigLevel() {
        return sigLevel;
    }

    public short getCtrlLevelLevel() {
        return ctrlLevelLevel;
    }

    public short getSimModel() {
        return simModel;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public float getD2Hx() {
        return D2Hx;
    }

    public float getD2Hy() {
        return D2Hy;
    }

    public short getActReqId() {
        return actReqId;
    }

    public short getActActId() {
        return actActId;
    }

    public short getCmdMod() {
        return cmdMod;
    }

    public short getModReqId() {
        return modReqId;
    }

    public short getIsSoaringUp() {
        return isSoaringUp;
    }

    public short getEagleTempLevel() {
        return eagleTempLevel;
    }

    @Override
    public String toString() {
        return "ControllerPacket{" +
                "ctrlTick=" + ctrlTick +
                ", ctrlPitch=" + ctrlPitch +
                ", ctrlRoll=" + ctrlRoll +
                ", ctrlYaw=" + ctrlYaw +
                ", ctrlThrottle=" + ctrlThrottle +
                ", ctrlMode=" + ctrlMode +
                ", modeSwitch=" + modeSwitch +
                ", motorState=" + motorState +
                ", sigLevel=" + sigLevel +
                ", ctrlLevelLevel=" + ctrlLevelLevel +
                ", simModel=" + simModel +
                ", maxHeight=" + maxHeight +
                ", maxRadius=" + maxRadius +
                ", D2Hx=" + D2Hx +
                ", D2Hy=" + D2Hy +
                ", actReqId=" + actReqId +
                ", actActId=" + actActId +
                ", cmdMod=" + cmdMod +
                ", modReqId=" + modReqId +
                ", isSoaringUp=" + isSoaringUp +
                ", eagleTempLevel=" + eagleTempLevel +
                '}';
    }
}
