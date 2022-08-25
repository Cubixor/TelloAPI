package me.cubixor.telloapi.utils;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteUtils {

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }

        return Arrays.copyOf(bytes, i + 1);
    }

    public static int connectBytes(byte... bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        try {
            return byteBuffer.getInt();
        } catch (BufferUnderflowException e) {
            return byteBuffer.getShort() & 0xffff;
        }
    }

    public static boolean startsWith(byte[] array, byte[] prefix) {
        if (array == prefix) {
            return true;
        }
        if (array == null || prefix == null) {
            return false;
        }
        int prefixLength = prefix.length;

        if (prefix.length > array.length) {
            return false;
        }

        for (int i = 0; i < prefixLength; i++) {
            if (array[i] != prefix[i]) {
                return false;
            }
        }

        return true;
    }

}