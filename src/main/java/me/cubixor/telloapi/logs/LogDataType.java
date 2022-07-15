package me.cubixor.telloapi.logs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public enum LogDataType {
    SIGNED_FLOAT_32(4, "fp32") {
        @Override
        public Object convertData(byte[] data) {
            return getByteBuffer(data).getFloat();
        }
    },
    SIGNED_FLOAT_64(8, "fp64") {
        @Override
        public Object convertData(byte[] data) {
            return getByteBuffer(data).getDouble();
        }
    },
    FLOAT(4, "float") {
        @Override
        public Object convertData(byte[] data) {
            return getByteBuffer(data).getFloat();
        }
    },
    UNSIGNED_INT_8(1, "uint8_t") {
        @Override
        public Object convertData(byte[] data) {
            return (short) (getByteBuffer(data).get() & 0xff);
        }
    },
    UNSIGNED_INT_16(2, "uint16_t") {
        @Override
        public Object convertData(byte[] data) {
            return (int) getByteBuffer(data).getShort() & 0xffff;
        }
    },
    UNSIGNED_INT_32(4, "uint32_t") {
        @Override
        public Object convertData(byte[] data) {
            return (long) getByteBuffer(data).getInt() & 0xffffffffL;
        }
    },
    SIGNED_INT_16(2, "int16_t") {
        @Override
        public Object convertData(byte[] data) {
            return getByteBuffer(data).getShort();
        }
    };

    private final int size;
    private final String name;

    LogDataType(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public static LogDataType matchByName(String name) {
        for (LogDataType logDataType : LogDataType.values()) {
            if (logDataType.getName().equals(name)) {
                return logDataType;
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public abstract Object convertData(byte[] data);

    protected ByteBuffer getByteBuffer(byte[] data) {
        return ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
    }
}
