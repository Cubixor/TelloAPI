package me.cubixor.telloapi.api;

public enum FlipDirection {
    FORWARD((byte) 0), LEFT((byte) 1), BACKWARD((byte) 2), RIGHT((byte) 3), FORWARD_LEFT((byte) 4), BACKWARD_LEFT((byte) 5), BACKWARD_RIGHT((byte) 6), FORWARD_RIGHT((byte) 7);

    private final byte data;

    FlipDirection(byte data) {
        this.data = data;
    }

    public byte getData() {
        return data;
    }
}
