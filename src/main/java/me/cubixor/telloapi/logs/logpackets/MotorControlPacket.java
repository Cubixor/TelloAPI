package me.cubixor.telloapi.logs.logpackets;

import me.cubixor.telloapi.Drone;
import me.cubixor.telloapi.logs.LogPacket;
import me.cubixor.telloapi.logs.LogPacketListener;

import java.util.Arrays;

public class MotorControlPacket extends LogPacket {

    public static final int PACKET_ID = 1307;

    private final float[] pwm = new float[8];

    public MotorControlPacket(Drone drone, int tick, Object[] data) {
        super(tick);

        for (int i = 0; i < 8; i++) {
            pwm[i] = (int) data[i] / 100.0f;
        }

        for (LogPacketListener logPacketListener : drone.getLogPacketListeners()) {
            logPacketListener.onMotorControlPacketReceived(this);
        }
    }

    public float[] getPwm() {
        return pwm;
    }

    @Override
    public String toString() {
        return "MotorControlPacket{" +
                "pwm=" + Arrays.toString(pwm) +
                '}';
    }
}
