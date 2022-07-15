package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class SerialApiInputsPacket extends LogPacket {

    public static final int PACKET_ID = 1002;

    private final short sdkCtrlF;
    private final short sdkRollX;
    private final short sdkPitchY;
    private final short sdkThrZ;
    private final short sdkYaw;
    private final short sdkFdfdX;
    private final short sdkFdfdY;
    private final short ctrlDev;
    private final short subMode;
    private final short openReq;
    private final short openAck;
    private final short cmdReq;
    private final short cmdAck;
    private final short avoidE;
    private final short bitS;
    private final short userFlag;
    private final short appFlag;
    private final short rcCnt;
    private final short supRc;
    private final short factCnt;
    private final short fTest;


    public SerialApiInputsPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        sdkCtrlF = (short) data[0];
        sdkRollX = (short) data[1];
        sdkPitchY = (short) data[2];
        sdkThrZ = (short) data[3];
        sdkYaw = (short) data[4];
        sdkFdfdX = (short) data[5];
        sdkFdfdY = (short) data[6];
        ctrlDev = (short) data[7];
        subMode = (short) data[8];
        openReq = (short) data[9];
        openAck = (short) data[10];
        cmdReq = (short) data[11];
        cmdAck = (short) data[12];
        avoidE = (short) data[13];
        bitS = (short) data[14];
        userFlag = (short) data[15];
        appFlag = (short) data[16];
        rcCnt = (short) data[17];
        supRc = (short) data[18];
        factCnt = (short) data[19];
        fTest = (short) data[20];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onSerialApiInputsPacketReceived(this);
        }
    }

    public short getSdkCtrlF() {
        return sdkCtrlF;
    }

    public short getSdkRollX() {
        return sdkRollX;
    }

    public short getSdkPitchY() {
        return sdkPitchY;
    }

    public short getSdkThrZ() {
        return sdkThrZ;
    }

    public short getSdkYaw() {
        return sdkYaw;
    }

    public short getSdkFdfdX() {
        return sdkFdfdX;
    }

    public short getSdkFdfdY() {
        return sdkFdfdY;
    }

    public short getCtrlDev() {
        return ctrlDev;
    }

    public short getSubMode() {
        return subMode;
    }

    public short getOpenReq() {
        return openReq;
    }

    public short getOpenAck() {
        return openAck;
    }

    public short getCmdReq() {
        return cmdReq;
    }

    public short getCmdAck() {
        return cmdAck;
    }

    public short getAvoidE() {
        return avoidE;
    }

    public short getBitS() {
        return bitS;
    }

    public short getUserFlag() {
        return userFlag;
    }

    public short getAppFlag() {
        return appFlag;
    }

    public short getRcCnt() {
        return rcCnt;
    }

    public short getSupRc() {
        return supRc;
    }

    public short getFactCnt() {
        return factCnt;
    }

    public short getfTest() {
        return fTest;
    }

    @Override
    public String toString() {
        return "SerialApiInputsPacket{" +
                "sdkCtrlF=" + sdkCtrlF +
                ", sdkRollX=" + sdkRollX +
                ", sdkPitchY=" + sdkPitchY +
                ", sdkThrZ=" + sdkThrZ +
                ", sdkYaw=" + sdkYaw +
                ", sdkFdfdX=" + sdkFdfdX +
                ", sdkFdfdY=" + sdkFdfdY +
                ", ctrlDev=" + ctrlDev +
                ", subMode=" + subMode +
                ", openReq=" + openReq +
                ", openAck=" + openAck +
                ", cmdReq=" + cmdReq +
                ", cmdAck=" + cmdAck +
                ", avoidE=" + avoidE +
                ", bitS=" + bitS +
                ", userFlag=" + userFlag +
                ", appFlag=" + appFlag +
                ", rcCnt=" + rcCnt +
                ", supRc=" + supRc +
                ", factCnt=" + factCnt +
                ", fTest=" + fTest +
                '}';
    }
}
