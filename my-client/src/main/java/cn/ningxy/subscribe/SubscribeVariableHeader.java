package cn.ningxy.subscribe;

import cn.ningxy.base.MqttPackageVariableHeader;
import com.google.common.primitives.Ints;

import java.util.Random;

/**
 * @author ningxy
 */
public class SubscribeVariableHeader implements MqttPackageVariableHeader {
    private final byte[] packetIdentifier = new byte[2];

    private SubscribeVariableHeader() {
        new Random().nextBytes(packetIdentifier);
    }

    public static SubscribeVariableHeader ofRandom() {
        return new SubscribeVariableHeader();
    }

    @Override
    public byte[] toByteArray() {
        return packetIdentifier;
    }

    public int getPacketIdentifierIntValue() {
        return Ints.fromByteArray(packetIdentifier);
    }
}
