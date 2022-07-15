package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class CtrlHorizDebugPacket extends LogPacket {

    public static final int PACKET_ID = 1300;

    private final short horizMode;
    private final short hovState;
    private final short hovFlag;
    private final float hovPx;
    private final float hovPy;
    private final float hovBrkT;
    private final float hovCfrmT;
    private final float posCmdx;
    private final float posCmdy;
    private final float velCmdx;
    private final float velCmdy;
    private final short hApiMode;
    private final short hApiFrm;
    private final float tiltCmdX;
    private final float tiltCmdY;
    private final short vCtrlMod;
    private final short trsFReq;
    private final short trsFFlg;
    private final short trsMod;
    private final short trsLckSt;
    private final float trsPCmd;
    private final float trsVCmd;
    private final float trsPLck;
    private final float trsLckT;
    private final short emgBrkF;
    private final short nearBnd;
    private final int modeAttiLimit;
    private final int avoidAttiLimit;
    private final int gimbalAttiLimit;
    private final int voLostAttiLimit;
    private final int compenAttiLimit;
    private final int gestureAttiLimit;
    private final int onGroundAttiLimit;
    private final int brakingAttiLimit;
    private final int lowLevelAttiLimit;
    private final int attiCompenForWind;
    private final int saturationAttiLimit;
    private final int batAttiLimit;
    private final int uncerKAdjustAttiLimit;
    private final int takeoffLimit;
    private final int finalAttiLimit;
    private final short avoidEn;
    private final short flwGmbF;
    private final short inFlwGmb;
    private final short GFTgtYaw;
    private final short GFCurYaw;
    private final float GTgtGyro;
    private final short gmbMode;
    private final float gmbJntP;
    private final float gmbJntY;
    private final float gmbOn;
    private final short gmbPos;
    private final short trackEn;
    private final float trckYawV;
    private final short ClTors;
    private final short apOsF;
    private final short avdObOsF;
    private final short HFenceOsF;
    private final short ROsF;
    private final short hLmtOsF;
    private final int trueRadLmt;

    public CtrlHorizDebugPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        horizMode = (short) data[0];
        hovState = (short) data[1];
        hovFlag = (short) data[2];
        hovPx = (float) data[3];
        hovPy = (float) data[4];
        hovBrkT = (float) data[5];
        hovCfrmT = (float) data[6];
        posCmdx = (float) data[7];
        posCmdy = (float) data[8];
        velCmdx = (float) data[9];
        velCmdy = (float) data[10];
        hApiMode = (short) data[11];
        hApiFrm = (short) data[12];
        tiltCmdX = (float) data[13];
        tiltCmdY = (float) data[14];
        vCtrlMod = (short) data[15];
        trsFReq = (short) data[16];
        trsFFlg = (short) data[17];
        trsMod = (short) data[18];
        trsLckSt = (short) data[19];
        trsPCmd = (float) data[20];
        trsVCmd = (float) data[21];
        trsPLck = (float) data[22];
        trsLckT = (float) data[23];
        emgBrkF = (short) data[24];
        nearBnd = (short) data[25];
        modeAttiLimit = (int) data[26];
        avoidAttiLimit = (int) data[27];
        gimbalAttiLimit = (int) data[28];
        voLostAttiLimit = (int) data[29];
        compenAttiLimit = (int) data[30];
        gestureAttiLimit = (int) data[31];
        onGroundAttiLimit = (int) data[32];
        brakingAttiLimit = (int) data[33];
        lowLevelAttiLimit = (int) data[34];
        attiCompenForWind = (int) data[35];
        saturationAttiLimit = (int) data[36];
        batAttiLimit = (int) data[37];
        uncerKAdjustAttiLimit = (int) data[38];
        takeoffLimit = (int) data[39];
        finalAttiLimit = (int) data[40];
        avoidEn = (short) data[41];
        flwGmbF = (short) data[42];
        inFlwGmb = (short) data[43];
        GFTgtYaw = (short) data[44];
        GFCurYaw = (short) data[45];
        GTgtGyro = (float) data[46];
        gmbMode = (short) data[47];
        gmbJntP = (float) data[48];
        gmbJntY = (float) data[49];
        gmbOn = (float) data[50];
        gmbPos = (short) data[51];
        trackEn = (short) data[52];
        trckYawV = (float) data[53];
        ClTors = (short) data[54];
        apOsF = (short) data[55];
        avdObOsF = (short) data[56];
        HFenceOsF = (short) data[57];
        ROsF = (short) data[58];
        hLmtOsF = (short) data[59];
        trueRadLmt = (int) data[60];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onCtrlHorizDebugPacketReceived(this);
        }
    }

    public short getHorizMode() {
        return horizMode;
    }

    public short getHovState() {
        return hovState;
    }

    public short getHovFlag() {
        return hovFlag;
    }

    public float getHovPx() {
        return hovPx;
    }

    public float getHovPy() {
        return hovPy;
    }

    public float getHovBrkT() {
        return hovBrkT;
    }

    public float getHovCfrmT() {
        return hovCfrmT;
    }

    public float getPosCmdx() {
        return posCmdx;
    }

    public float getPosCmdy() {
        return posCmdy;
    }

    public float getVelCmdx() {
        return velCmdx;
    }

    public float getVelCmdy() {
        return velCmdy;
    }

    public short gethApiMode() {
        return hApiMode;
    }

    public short gethApiFrm() {
        return hApiFrm;
    }

    public float getTiltCmdX() {
        return tiltCmdX;
    }

    public float getTiltCmdY() {
        return tiltCmdY;
    }

    public short getvCtrlMod() {
        return vCtrlMod;
    }

    public short getTrsFReq() {
        return trsFReq;
    }

    public short getTrsFFlg() {
        return trsFFlg;
    }

    public short getTrsMod() {
        return trsMod;
    }

    public short getTrsLckSt() {
        return trsLckSt;
    }

    public float getTrsPCmd() {
        return trsPCmd;
    }

    public float getTrsVCmd() {
        return trsVCmd;
    }

    public float getTrsPLck() {
        return trsPLck;
    }

    public float getTrsLckT() {
        return trsLckT;
    }

    public short getEmgBrkF() {
        return emgBrkF;
    }

    public short getNearBnd() {
        return nearBnd;
    }

    public int getModeAttiLimit() {
        return modeAttiLimit;
    }

    public int getAvoidAttiLimit() {
        return avoidAttiLimit;
    }

    public int getGimbalAttiLimit() {
        return gimbalAttiLimit;
    }

    public int getVoLostAttiLimit() {
        return voLostAttiLimit;
    }

    public int getCompenAttiLimit() {
        return compenAttiLimit;
    }

    public int getGestureAttiLimit() {
        return gestureAttiLimit;
    }

    public int getOnGroundAttiLimit() {
        return onGroundAttiLimit;
    }

    public int getBrakingAttiLimit() {
        return brakingAttiLimit;
    }

    public int getLowLevelAttiLimit() {
        return lowLevelAttiLimit;
    }

    public int getAttiCompenForWind() {
        return attiCompenForWind;
    }

    public int getSaturationAttiLimit() {
        return saturationAttiLimit;
    }

    public int getBatAttiLimit() {
        return batAttiLimit;
    }

    public int getUncerKAdjustAttiLimit() {
        return uncerKAdjustAttiLimit;
    }

    public int getTakeoffLimit() {
        return takeoffLimit;
    }

    public int getFinalAttiLimit() {
        return finalAttiLimit;
    }

    public short getAvoidEn() {
        return avoidEn;
    }

    public short getFlwGmbF() {
        return flwGmbF;
    }

    public short getInFlwGmb() {
        return inFlwGmb;
    }

    public short getGFTgtYaw() {
        return GFTgtYaw;
    }

    public short getGFCurYaw() {
        return GFCurYaw;
    }

    public float getGTgtGyro() {
        return GTgtGyro;
    }

    public short getGmbMode() {
        return gmbMode;
    }

    public float getGmbJntP() {
        return gmbJntP;
    }

    public float getGmbJntY() {
        return gmbJntY;
    }

    public float getGmbOn() {
        return gmbOn;
    }

    public short getGmbPos() {
        return gmbPos;
    }

    public short getTrackEn() {
        return trackEn;
    }

    public float getTrckYawV() {
        return trckYawV;
    }

    public short getClTors() {
        return ClTors;
    }

    public short getApOsF() {
        return apOsF;
    }

    public short getAvdObOsF() {
        return avdObOsF;
    }

    public short getHFenceOsF() {
        return HFenceOsF;
    }

    public short getROsF() {
        return ROsF;
    }

    public short gethLmtOsF() {
        return hLmtOsF;
    }

    public int getTrueRadLmt() {
        return trueRadLmt;
    }

    @Override
    public String toString() {
        return "CtrlHorizDebugPacket{" +
                "horizMode=" + horizMode +
                ", hovState=" + hovState +
                ", hovFlag=" + hovFlag +
                ", hovPx=" + hovPx +
                ", hovPy=" + hovPy +
                ", hovBrkT=" + hovBrkT +
                ", hovCfrmT=" + hovCfrmT +
                ", posCmdx=" + posCmdx +
                ", posCmdy=" + posCmdy +
                ", velCmdx=" + velCmdx +
                ", velCmdy=" + velCmdy +
                ", hApiMode=" + hApiMode +
                ", hApiFrm=" + hApiFrm +
                ", tiltCmdX=" + tiltCmdX +
                ", tiltCmdY=" + tiltCmdY +
                ", vCtrlMod=" + vCtrlMod +
                ", trsFReq=" + trsFReq +
                ", trsFFlg=" + trsFFlg +
                ", trsMod=" + trsMod +
                ", trsLckSt=" + trsLckSt +
                ", trsPCmd=" + trsPCmd +
                ", trsVCmd=" + trsVCmd +
                ", trsPLck=" + trsPLck +
                ", trsLckT=" + trsLckT +
                ", emgBrkF=" + emgBrkF +
                ", nearBnd=" + nearBnd +
                ", modeAttiLimit=" + modeAttiLimit +
                ", avoidAttiLimit=" + avoidAttiLimit +
                ", gimbalAttiLimit=" + gimbalAttiLimit +
                ", voLostAttiLimit=" + voLostAttiLimit +
                ", compenAttiLimit=" + compenAttiLimit +
                ", gestureAttiLimit=" + gestureAttiLimit +
                ", onGroundAttiLimit=" + onGroundAttiLimit +
                ", brakingAttiLimit=" + brakingAttiLimit +
                ", lowLevelAttiLimit=" + lowLevelAttiLimit +
                ", attiCompenForWind=" + attiCompenForWind +
                ", saturationAttiLimit=" + saturationAttiLimit +
                ", batAttiLimit=" + batAttiLimit +
                ", uncerKAdjustAttiLimit=" + uncerKAdjustAttiLimit +
                ", takeoffLimit=" + takeoffLimit +
                ", finalAttiLimit=" + finalAttiLimit +
                ", avoidEn=" + avoidEn +
                ", flwGmbF=" + flwGmbF +
                ", inFlwGmb=" + inFlwGmb +
                ", GFTgtYaw=" + GFTgtYaw +
                ", GFCurYaw=" + GFCurYaw +
                ", GTgtGyro=" + GTgtGyro +
                ", gmbMode=" + gmbMode +
                ", gmbJntP=" + gmbJntP +
                ", gmbJntY=" + gmbJntY +
                ", gmbOn=" + gmbOn +
                ", gmbPos=" + gmbPos +
                ", trackEn=" + trackEn +
                ", trckYawV=" + trckYawV +
                ", ClTors=" + ClTors +
                ", apOsF=" + apOsF +
                ", avdObOsF=" + avdObOsF +
                ", HFenceOsF=" + HFenceOsF +
                ", ROsF=" + ROsF +
                ", hLmtOsF=" + hLmtOsF +
                ", trueRadLmt=" + trueRadLmt +
                '}';
    }
}
