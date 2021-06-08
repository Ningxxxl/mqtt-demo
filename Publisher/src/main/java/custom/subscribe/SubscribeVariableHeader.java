package custom.subscribe;

import com.google.common.primitives.Ints;

import java.util.Random;

/**
 * @author ningxy
 */
public class SubscribeVariableHeader {
    private final byte[] packetIdentifier = new byte[2];

    private SubscribeVariableHeader() {
        new Random().nextBytes(packetIdentifier);
    }

    public static SubscribeVariableHeader of() {
        return new SubscribeVariableHeader();
    }

    public byte[] toByteArray() {
        return packetIdentifier;
    }

    public int getPacketIdentifierIntValue() {
        return Ints.fromByteArray(packetIdentifier);
    }
}
