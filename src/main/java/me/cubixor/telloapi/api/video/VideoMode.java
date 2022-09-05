package me.cubixor.telloapi.api.video;

public enum VideoMode {
    PHOTO(960, 720), VIDEO(1280, 720);

    private final int width;
    private final int height;

    VideoMode(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
