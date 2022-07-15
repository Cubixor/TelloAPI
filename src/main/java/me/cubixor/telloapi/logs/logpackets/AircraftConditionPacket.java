package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class AircraftConditionPacket extends LogPacket {

    public static final int PACKET_ID = 1001;

    protected short intFsm;
    protected short fsmState;
    protected short lastFsm;
    protected short nearGnd;
    protected short UPState;
    protected float UPAccT;
    protected float UpTFT;
    protected short landState;
    protected short safeFltr;


    public AircraftConditionPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        intFsm = (short) data[0];
        fsmState = (short) data[1];
        lastFsm = (short) data[2];
        nearGnd = (short) data[3];
        UPState = (short) data[4];
        UPAccT = (float) data[5];
        UpTFT = (float) data[6];
        landState = (short) data[7];
        safeFltr = (short) data[8];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onAircraftConditionPacketReceived(this);
        }
    }

    public short getIntFsm() {
        return intFsm;
    }

    public short getFsmState() {
        return fsmState;
    }

    public short getLastFsm() {
        return lastFsm;
    }

    public short getNearGnd() {
        return nearGnd;
    }

    public short getUPState() {
        return UPState;
    }

    public float getUPAccT() {
        return UPAccT;
    }

    public float getUpTFT() {
        return UpTFT;
    }

    public short getLandState() {
        return landState;
    }

    public short getSafeFltr() {
        return safeFltr;
    }

    @Override
    public String toString() {
        return "AircraftConditionPacket{" +
                "intFsm=" + intFsm +
                ", fsmState=" + fsmState +
                ", lastFsm=" + lastFsm +
                ", nearGnd=" + nearGnd +
                ", UPState=" + UPState +
                ", UPAccT=" + UPAccT +
                ", UpTFT=" + UpTFT +
                ", landState=" + landState +
                ", safeFltr=" + safeFltr +
                '}';
    }
}
