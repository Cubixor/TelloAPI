package me.cubixor.telloapi.logs;

public abstract class LogPacket {

    private final int tick;

    public LogPacket(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }
}
