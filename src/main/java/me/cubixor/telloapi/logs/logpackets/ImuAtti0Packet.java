package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class ImuAtti0Packet extends LogPacket {

    public static final int PACKET_ID = 2048;

    private final double longitude;
    private final double latitude;
    private final float pressure;
    private final float accelX;
    private final float accelY;
    private final float accelZ;
    private final float gyroX;
    private final float gyroY;
    private final float gyroZ;
    private final float baroAlti;
    private final float quatW;
    private final float quatX;
    private final float quatY;
    private final float quatZ;
    private final float agX;
    private final float agY;
    private final float agZ;
    private final float velN;
    private final float velE;
    private final float velD;
    private final float gbX;
    private final float gbY;
    private final float gbZ;
    private final short magX;
    private final short magY;
    private final short magZ;
    private final float imuTemp;
    private final short ty;
    private final short tz;
    private final int sensorStat;
    private final int filterStat;
    private final int svn;
    private final int attiCnt;

    public ImuAtti0Packet(Drone drone, int tick, Object[] data) {
        super(tick);

        longitude = (double) data[0];
        latitude = (double) data[1];
        pressure = (float) data[2];
        accelX = (float) data[3];
        accelY = (float) data[4];
        accelZ = (float) data[5];
        gyroX = (float) data[6];
        gyroY = (float) data[7];
        gyroZ = (float) data[8];
        baroAlti = (float) data[9];
        quatW = (float) data[10];
        quatX = (float) data[11];
        quatY = (float) data[12];
        quatZ = (float) data[13];
        agX = (float) data[14];
        agY = (float) data[15];
        agZ = (float) data[16];
        velN = (float) data[17];
        velE = (float) data[18];
        velD = (float) data[19];
        gbX = (float) data[20];
        gbY = (float) data[21];
        gbZ = (float) data[22];
        magX = (short) data[23];
        magY = (short) data[24];
        magZ = (short) data[25];
        imuTemp = ((short) data[26]) / 100.0f;
        ty = (short) data[27];
        tz = (short) data[28];
        sensorStat = (int) data[29];
        filterStat = (int) data[30];
        svn = (int) data[31];
        attiCnt = (int) data[32];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onImuAtti0PacketReceived(this);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public float getPressure() {
        return pressure;
    }

    public float getAccelX() {
        return accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public float getAccelZ() {
        return accelZ;
    }

    public float getGyroX() {
        return gyroX;
    }

    public float getGyroY() {
        return gyroY;
    }

    public float getGyroZ() {
        return gyroZ;
    }

    public float getBaroAlti() {
        return baroAlti;
    }

    public float getQuatW() {
        return quatW;
    }

    public float getQuatX() {
        return quatX;
    }

    public float getQuatY() {
        return quatY;
    }

    public float getQuatZ() {
        return quatZ;
    }

    public float getAgX() {
        return agX;
    }

    public float getAgY() {
        return agY;
    }

    public float getAgZ() {
        return agZ;
    }

    public float getVelN() {
        return velN;
    }

    public float getVelE() {
        return velE;
    }

    public float getVelD() {
        return velD;
    }

    public float getGbX() {
        return gbX;
    }

    public float getGbY() {
        return gbY;
    }

    public float getGbZ() {
        return gbZ;
    }

    public short getMagX() {
        return magX;
    }

    public short getMagY() {
        return magY;
    }

    public short getMagZ() {
        return magZ;
    }

    public float getImuTemp() {
        return imuTemp;
    }

    public short getTy() {
        return ty;
    }

    public short getTz() {
        return tz;
    }

    public int getSensorStat() {
        return sensorStat;
    }

    public int getFilterStat() {
        return filterStat;
    }

    public int getSvn() {
        return svn;
    }

    public int getAttiCnt() {
        return attiCnt;
    }

    @Override
    public String toString() {
        return "ImuAtti0Packet{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", pressure=" + pressure +
                ", accelX=" + accelX +
                ", accelY=" + accelY +
                ", accelZ=" + accelZ +
                ", gyroX=" + gyroX +
                ", gyroY=" + gyroY +
                ", gyroZ=" + gyroZ +
                ", baroAlti=" + baroAlti +
                ", quatW=" + quatW +
                ", quatX=" + quatX +
                ", quatY=" + quatY +
                ", quatZ=" + quatZ +
                ", agX=" + agX +
                ", agY=" + agY +
                ", agZ=" + agZ +
                ", velN=" + velN +
                ", velE=" + velE +
                ", velD=" + velD +
                ", gbX=" + gbX +
                ", gbY=" + gbY +
                ", gbZ=" + gbZ +
                ", magX=" + magX +
                ", magY=" + magY +
                ", magZ=" + magZ +
                ", imuTemp=" + imuTemp +
                ", ty=" + ty +
                ", tz=" + tz +
                ", sensorStat=" + sensorStat +
                ", filterStat=" + filterStat +
                ", svn=" + svn +
                ", attiCnt=" + attiCnt +
                '}';
    }
}
