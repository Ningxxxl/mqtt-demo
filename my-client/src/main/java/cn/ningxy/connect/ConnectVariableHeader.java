package cn.ningxy.connect;

import cn.ningxy.base.MqttPackageVariableHeader;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;

/**
 * @author ningxy
 */
public class ConnectVariableHeader implements MqttPackageVariableHeader {
    private static final byte[] PROTOCOL_NAME_BYTES;

    private static final byte PROTOCOL_LEVEL_BYTE;

    private static final byte CONNECT_FLAG_BITS;

    private static final short KEEP_ALIVE_BYTE;

    static {
        PROTOCOL_NAME_BYTES = new byte[]{
                // MSB (0) | LSB (4)  |    'M'    |    'Q'    |    'T'    |    'T'    |
                0b00000000, 0b00000100, 0b01001101, 0b01010001, 0b01010100, 0b01010100
        };
        // MQTT v3.1.1 == 4(0x04)
        PROTOCOL_LEVEL_BYTE = 0b00000100;
        CONNECT_FLAG_BITS = ConnectFlags.getFlagByte(ConnectFlags.CLEAN_SESSION);
        KEEP_ALIVE_BYTE = 60;
    }

    private ConnectVariableHeader() {
    }

    public static ConnectVariableHeader ofDefault() {
        return new ConnectVariableHeader();
    }

    @Override
    public byte[] toByteArray() {
        return Bytes.concat(
                PROTOCOL_NAME_BYTES,
                new byte[]{PROTOCOL_LEVEL_BYTE},
                new byte[]{CONNECT_FLAG_BITS},
                Shorts.toByteArray(KEEP_ALIVE_BYTE)
        );
    }
}

class ConnectFlags {
    private static final byte QOS_0 = 0b000_00_000 & 0xFF;
    private static final byte QOS_1 = 0b000_01_000 & 0xFF;
    private static final byte QOS_2 = 0b000_10_000 & 0xFF;

    /**
     * 这里必须强转，为1的话超过了 signed byte 范围
     */
    public static final byte USERNAME_FLAG = (byte) (0b1_0000000 & 0xFF);
    public static final byte PASSWORD_FLAG = 0b0_1_000000 & 0xFF;
    public static final byte WILL_RETAIN_FLAG = 0b00_1_00000 & 0xFF;
    public static final byte WILL_QOS_FLAG = QOS_0;
    public static final byte WILL_FLAG = 0b00000_1_00 & 0xFF;
    public static final byte CLEAN_SESSION = 0b000000_1_0 & 0xFF;
    public static final byte RESERVED = 0b0000000_0 & 0xFF;

    public static byte getFlagByte(short... flags) {
        byte flag = 0;
        for (short f : flags) {
            flag = (byte) ((flag | f) & 0xFF);
        }
        return flag;
    }
}
