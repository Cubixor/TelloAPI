package me.cubixor.telloapi.api.video;

public enum SmartVideoMode {
    VIDEO_360((byte) 1), CIRCLE((byte) 2), UP_AND_OUT((byte) 3);

    private final byte data;

    SmartVideoMode(byte data) {
        this.data = data;
    }

    public static SmartVideoMode getByID(byte id) {
        for (SmartVideoMode smartVideoMode : SmartVideoMode.values()) {
            if (smartVideoMode.getData() == id) {
                return smartVideoMode;
            }
        }
        return null;
    }

    public byte getData() {
        return data;
    }
}