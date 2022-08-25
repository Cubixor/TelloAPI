import me.cubixor.telloapi.utils.ByteUtils;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ActivationTimeTest {

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] m5072a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static short m5068a(byte[] bArr) {
        return (short) ((bArr[0] & 255) | (65280 & (bArr[1] << 8)));
    }

    public static String m5067a(byte[] bArr, String str) {
        return new String(bArr, Charset.forName(str));
    }

    @Test
    public void decodeActivationTime() {
        byte[] payload = hexStringToByteArray("00E2070C000C000800270019000000000000000000000000000000000000E2070C000C0008002700190000000000000000000000000000000000");

        int year = ByteUtils.connectBytes(payload[1], payload[2]);
        int month = ByteUtils.connectBytes(payload[3], payload[4]);
        int day = ByteUtils.connectBytes(payload[5], payload[6]);

        //Not sure about these four
        int hour = ByteUtils.connectBytes(payload[7], payload[8]);
        int minute = ByteUtils.connectBytes(payload[9], payload[10]);
        int second = ByteUtils.connectBytes(payload[11], payload[12]);
        int zero = ByteUtils.connectBytes(payload[13], payload[14]);

        //Serial number seems to be always zero
        String sn = new String(Arrays.copyOfRange(payload, 15, 30), StandardCharsets.UTF_8);

        LocalDateTime activationTime = LocalDateTime.of(year, month, day, hour, minute, second, zero);
        System.out.println(activationTime);
        assert activationTime.equals(LocalDateTime.of(2018, 12, 12, 8, 39, 25, 0));
    }
}
