package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

public class BatteryInfoPacket extends LogPacket {

    public static final int PACKET_ID = 1710;
    private final float batteryVoltage;
    private final int batteryCapacity;

    public BatteryInfoPacket(Drone drone, int tick, Object[] data) {
        super(tick);
        batteryVoltage = (float) data[0];
        batteryCapacity = (int) data[1];

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onBatteryInfoPacketReceived(this);
        }
    }

    public float getBatteryVoltage() {
        return batteryVoltage;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    @Override
    public String toString() {
        return "BatteryInfoPacket{" +
                "batteryVoltage=" + batteryVoltage +
                ", batteryCapacity=" + batteryCapacity +
                '}';
    }
}
