package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class OsdDataPacket extends LogPacket {

    public static final int PACKET_ID = 12;

    private final double longitude;
    private final double latitude;
    private final short relativeHeight;
    private final short vgx;
    private final short vgy;
    private final short vgz;
    private final short pitch;
    private final short roll;
    private final short yaw;
    private final short mode1;
    private final short latestCmd;
    private final long controllerState;
    private final short gpsNums;
    private final short goHomeLandingReason;
    private final short startFailReason;
    private final short controllerStateExt;
    private final short ctrlTick;
    private final short ultrasonicHeight;
    private final int motorStartupTime;
    private final short motorStartupTimes;
    private final short batAlarm1;
    private final short batAlarm2;
    private final short versionMatch;
    private final short productType;
    private final short imuInitFailReason;
    private final short stopMotorReason;
    private final short motorStartErrorCode;
    private final short sdkCtrlDev;
    private final short yawRate;

    public OsdDataPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        longitude = (double) data[0];
        latitude = (double) data[1];
        relativeHeight = (short) data[2];
        vgx = (short) data[3];
        vgy = (short) data[4];
        vgz = (short) data[5];
        pitch = (short) data[6];
        roll = (short) data[7];
        yaw = (short) data[8];
        mode1 = (short) data[9];
        latestCmd = (short) data[10];
        controllerState = (long) data[11];
        gpsNums = (short) data[12];
        goHomeLandingReason = (short) data[13];
        startFailReason = (short) data[14];
        controllerStateExt = (short) data[15];
        ctrlTick = (short) data[16];
        ultrasonicHeight = (short) data[17];
        motorStartupTime = (int) data[18];
        motorStartupTimes = (short) data[19];
        batAlarm1 = (short) data[20];
        batAlarm2 = (short) data[21];
        versionMatch = (short) data[22];
        productType = (short) data[23];
        imuInitFailReason = (short) data[24];
        stopMotorReason = (short) data[25];
        motorStartErrorCode = (short) data[26];
        sdkCtrlDev = (short) data[27];
        yawRate = (short) data[28];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onOsdDataPacketReceived(this);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public short getRelativeHeight() {
        return relativeHeight;
    }

    public short getVgx() {
        return vgx;
    }

    public short getVgy() {
        return vgy;
    }

    public short getVgz() {
        return vgz;
    }

    public short getPitch() {
        return pitch;
    }

    public short getRoll() {
        return roll;
    }

    public short getYaw() {
        return yaw;
    }

    public short getMode1() {
        return mode1;
    }

    public short getLatestCmd() {
        return latestCmd;
    }

    public long getControllerState() {
        return controllerState;
    }

    public short getGpsNums() {
        return gpsNums;
    }

    public short getGoHomeLandingReason() {
        return goHomeLandingReason;
    }

    public short getStartFailReason() {
        return startFailReason;
    }

    public short getControllerStateExt() {
        return controllerStateExt;
    }

    public short getCtrlTick() {
        return ctrlTick;
    }

    public short getUltrasonicHeight() {
        return ultrasonicHeight;
    }

    public int getMotorStartupTime() {
        return motorStartupTime;
    }

    public short getMotorStartupTimes() {
        return motorStartupTimes;
    }

    public short getBatAlarm1() {
        return batAlarm1;
    }

    public short getBatAlarm2() {
        return batAlarm2;
    }

    public short getVersionMatch() {
        return versionMatch;
    }

    public short getProductType() {
        return productType;
    }

    public short getImuInitFailReason() {
        return imuInitFailReason;
    }

    public short getStopMotorReason() {
        return stopMotorReason;
    }

    public short getMotorStartErrorCode() {
        return motorStartErrorCode;
    }

    public short getSdkCtrlDev() {
        return sdkCtrlDev;
    }

    public short getYawRate() {
        return yawRate;
    }

    @Override
    public String toString() {
        return "OsdDataPacket{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", relativeHeight=" + relativeHeight +
                ", vgx=" + vgx +
                ", vgy=" + vgy +
                ", vgz=" + vgz +
                ", pitch=" + pitch +
                ", roll=" + roll +
                ", yaw=" + yaw +
                ", mode1=" + mode1 +
                ", latestCmd=" + latestCmd +
                ", controllerState=" + controllerState +
                ", gpsNums=" + gpsNums +
                ", goHomeLandingReason=" + goHomeLandingReason +
                ", startFailReason=" + startFailReason +
                ", controllerStateExt=" + controllerStateExt +
                ", ctrlTick=" + ctrlTick +
                ", ultrasonicHeight=" + ultrasonicHeight +
                ", motorStartupTime=" + motorStartupTime +
                ", motorStartupTimes=" + motorStartupTimes +
                ", batAlarm1=" + batAlarm1 +
                ", batAlarm2=" + batAlarm2 +
                ", versionMatch=" + versionMatch +
                ", productType=" + productType +
                ", imuInitFailReason=" + imuInitFailReason +
                ", stopMotorReason=" + stopMotorReason +
                ", motorStartErrorCode=" + motorStartErrorCode +
                ", sdkCtrlDev=" + sdkCtrlDev +
                ", yawRate=" + yawRate +
                '}';
    }
}
