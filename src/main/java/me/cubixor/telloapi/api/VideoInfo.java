package me.cubixor.telloapi.api;

public abstract class VideoInfo {

    private static final float[] exposureValues = new float[]{-3.0f, -2.7f, -2.3f, -2.0f, -1.7f, -1.3f, -1.0f, -0.7f, -0.3f, 0f, 0.3f, 0.7f, 1.0f, 1.3f, 1.7f, 2.0f, 2.3f, 2.7f, 3.0f};

    public static float[] getExposureValues() {
        return exposureValues;
    }

    public abstract BitRate getBitRate();

    public abstract float getExposure();

    public abstract int getIFrameInterval();

    public abstract VideoMode getVideoMode();

    public abstract SmartVideoMode getSmartVideoMode();

    public abstract boolean isSmartVideoRunning();

    public abstract void startVideoStream(int iFrameInterval);

    public abstract void updateIFrameInterval(int iFrameInterval);


    public enum BitRate {
        AUTO, MBPS_1, MBPS_1_5, MBPS_2, MBPS_3, MBPS_4
    }

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
}
