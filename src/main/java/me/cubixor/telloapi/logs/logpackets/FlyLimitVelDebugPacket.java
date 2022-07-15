package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class FlyLimitVelDebugPacket extends LogPacket {

    public static final int PACKET_ID = 1303;

    private final short radEn;
    private final short radWork;
    private final short radDirX;
    private final short radDirY;
    private final short radDisToObj;
    private final short AP0en;
    private final short AP0work;
    private final short AP0dirX;
    private final short AP0dirY;
    private final short AP0disToObj;
    private final short AP1en;
    private final short AP1work;
    private final short AP1dirX;
    private final short AP1dirY;
    private final short AP1disToObj;
    private final short AP2en;
    private final short AP2work;
    private final short AP2dirX;
    private final short AP2dirY;
    private final short AP2disToObj;
    private final short AO0en;
    private final short AO0work;
    private final short AO0dirX;
    private final short AO0dirY;
    private final short AO0disToObj;
    private final short AO1en;
    private final short AO1work;
    private final short AO1dirX;
    private final short AO1dirY;
    private final short AO1disToObj;
    private final short AO2en;
    private final short AO2work;
    private final short AO2dirX;
    private final short AO2dirY;
    private final short AO2disToObj;
    private final short AO3en;
    private final short AO3work;
    private final short AO3dirX;
    private final short AO3dirY;
    private final short AO3disToObj;

    public FlyLimitVelDebugPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        radEn = (short) data[0];
        radWork = (short) data[1];
        radDirX = (short) data[2];
        radDirY = (short) data[3];
        radDisToObj = (short) data[4];
        AP0en = (short) data[5];
        AP0work = (short) data[6];
        AP0dirX = (short) data[7];
        AP0dirY = (short) data[8];
        AP0disToObj = (short) data[9];
        AP1en = (short) data[10];
        AP1work = (short) data[11];
        AP1dirX = (short) data[12];
        AP1dirY = (short) data[13];
        AP1disToObj = (short) data[14];
        AP2en = (short) data[15];
        AP2work = (short) data[16];
        AP2dirX = (short) data[17];
        AP2dirY = (short) data[18];
        AP2disToObj = (short) data[19];
        AO0en = (short) data[20];
        AO0work = (short) data[21];
        AO0dirX = (short) data[22];
        AO0dirY = (short) data[23];
        AO0disToObj = (short) data[24];
        AO1en = (short) data[25];
        AO1work = (short) data[26];
        AO1dirX = (short) data[27];
        AO1dirY = (short) data[28];
        AO1disToObj = (short) data[29];
        AO2en = (short) data[30];
        AO2work = (short) data[31];
        AO2dirX = (short) data[32];
        AO2dirY = (short) data[33];
        AO2disToObj = (short) data[34];
        AO3en = (short) data[35];
        AO3work = (short) data[36];
        AO3dirX = (short) data[37];
        AO3dirY = (short) data[38];
        AO3disToObj = (short) data[39];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onFlyLimitVelDebugPacketReceived(this);
        }
    }

    public short getRadEn() {
        return radEn;
    }

    public short getRadWork() {
        return radWork;
    }

    public short getRadDirX() {
        return radDirX;
    }

    public short getRadDirY() {
        return radDirY;
    }

    public short getRadDisToObj() {
        return radDisToObj;
    }

    public short getAP0en() {
        return AP0en;
    }

    public short getAP0work() {
        return AP0work;
    }

    public short getAP0dirX() {
        return AP0dirX;
    }

    public short getAP0dirY() {
        return AP0dirY;
    }

    public short getAP0disToObj() {
        return AP0disToObj;
    }

    public short getAP1en() {
        return AP1en;
    }

    public short getAP1work() {
        return AP1work;
    }

    public short getAP1dirX() {
        return AP1dirX;
    }

    public short getAP1dirY() {
        return AP1dirY;
    }

    public short getAP1disToObj() {
        return AP1disToObj;
    }

    public short getAP2en() {
        return AP2en;
    }

    public short getAP2work() {
        return AP2work;
    }

    public short getAP2dirX() {
        return AP2dirX;
    }

    public short getAP2dirY() {
        return AP2dirY;
    }

    public short getAP2disToObj() {
        return AP2disToObj;
    }

    public short getAO0en() {
        return AO0en;
    }

    public short getAO0work() {
        return AO0work;
    }

    public short getAO0dirX() {
        return AO0dirX;
    }

    public short getAO0dirY() {
        return AO0dirY;
    }

    public short getAO0disToObj() {
        return AO0disToObj;
    }

    public short getAO1en() {
        return AO1en;
    }

    public short getAO1work() {
        return AO1work;
    }

    public short getAO1dirX() {
        return AO1dirX;
    }

    public short getAO1dirY() {
        return AO1dirY;
    }

    public short getAO1disToObj() {
        return AO1disToObj;
    }

    public short getAO2en() {
        return AO2en;
    }

    public short getAO2work() {
        return AO2work;
    }

    public short getAO2dirX() {
        return AO2dirX;
    }

    public short getAO2dirY() {
        return AO2dirY;
    }

    public short getAO2disToObj() {
        return AO2disToObj;
    }

    public short getAO3en() {
        return AO3en;
    }

    public short getAO3work() {
        return AO3work;
    }

    public short getAO3dirX() {
        return AO3dirX;
    }

    public short getAO3dirY() {
        return AO3dirY;
    }

    public short getAO3disToObj() {
        return AO3disToObj;
    }

    @Override
    public String toString() {
        return "FlyLimitVelDebugPacket{" +
                "radEn=" + radEn +
                ", radWork=" + radWork +
                ", radDirX=" + radDirX +
                ", radDirY=" + radDirY +
                ", radDisToObj=" + radDisToObj +
                ", AP0en=" + AP0en +
                ", AP0work=" + AP0work +
                ", AP0dirX=" + AP0dirX +
                ", AP0dirY=" + AP0dirY +
                ", AP0disToObj=" + AP0disToObj +
                ", AP1en=" + AP1en +
                ", AP1work=" + AP1work +
                ", AP1dirX=" + AP1dirX +
                ", AP1dirY=" + AP1dirY +
                ", AP1disToObj=" + AP1disToObj +
                ", AP2en=" + AP2en +
                ", AP2work=" + AP2work +
                ", AP2dirX=" + AP2dirX +
                ", AP2dirY=" + AP2dirY +
                ", AP2disToObj=" + AP2disToObj +
                ", AO0en=" + AO0en +
                ", AO0work=" + AO0work +
                ", AO0dirX=" + AO0dirX +
                ", AO0dirY=" + AO0dirY +
                ", AO0disToObj=" + AO0disToObj +
                ", AO1en=" + AO1en +
                ", AO1work=" + AO1work +
                ", AO1dirX=" + AO1dirX +
                ", AO1dirY=" + AO1dirY +
                ", AO1disToObj=" + AO1disToObj +
                ", AO2en=" + AO2en +
                ", AO2work=" + AO2work +
                ", AO2dirX=" + AO2dirX +
                ", AO2dirY=" + AO2dirY +
                ", AO2disToObj=" + AO2disToObj +
                ", AO3en=" + AO3en +
                ", AO3work=" + AO3work +
                ", AO3dirX=" + AO3dirX +
                ", AO3dirY=" + AO3dirY +
                ", AO3disToObj=" + AO3disToObj +
                '}';
    }
}
