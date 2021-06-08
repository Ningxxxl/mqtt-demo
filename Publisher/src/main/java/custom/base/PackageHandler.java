package custom.base;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Ints;
import custom.ControlPacketTypeEnum;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;

/**
 * @author ningxy
 */
public class PackageHandler {
    public static void resolve(byte[] rawBytes) {
        while (rawBytes.length > 0) {
            String rawBytesHexString = BaseEncoding.base16().encode(rawBytes);
            ControlPacketTypeEnum controlPacketType = getControlPacketType(rawBytes[0]);
            AbstractMap.SimpleEntry<Integer, Integer> entry = getPackageLength(rawBytes);
            int packageLength = entry.getKey();
            int remainingLength = entry.getValue();

            System.out.println();
            System.out.printf(" ==================== Handle Response Package ==================== %n");
            System.out.printf(" RawBytes(Hex): %s %n", rawBytesHexString);
            System.out.printf(" ControlPacketType = %s %n", controlPacketType);

            if (controlPacketType.equals(ControlPacketTypeEnum.PUBLISH)) {
                handlePublishPacket(rawBytes, remainingLength);
            }

            if (packageLength >= rawBytes.length) {
                break;
            }
            rawBytes = Arrays.copyOfRange(rawBytes, packageLength, rawBytes.length);
        }
    }

    private static ControlPacketTypeEnum getControlPacketType(byte byte1) {
        int typeCode = ((byte1 >>> 4) & 0b0000_1111);
        return ControlPacketTypeEnum.getByCode(typeCode);
    }

    private static AbstractMap.SimpleEntry<Integer, Integer> getPackageLength(byte[] rawBytes) {
        int remainingLength = 0, i = 1;
        do {
            remainingLength += (rawBytes[i] & 0b0111_1111);
        } while ((rawBytes[i++] >>> 7) != 0);
        return new AbstractMap.SimpleEntry<>(i + remainingLength, remainingLength);
    }

    private static void handlePublishPacket(byte[] rawBytes, int remainingLength) {
        int offset = rawBytes.length - remainingLength;
        int topicNameLength = Ints.fromBytes((byte) 0, (byte) 0, rawBytes[offset], rawBytes[offset + 1]);
        int messageLength = remainingLength - 2 - topicNameLength;

        String topicName = new String(rawBytes, offset + 2, topicNameLength);
        String message = new String(rawBytes, offset + 2 + topicNameLength, messageLength, StandardCharsets.UTF_8);

        System.out.printf(" Topic Name        = %s %n", topicName);
        System.out.printf(" Message           = %s %n", message);
    }
}
