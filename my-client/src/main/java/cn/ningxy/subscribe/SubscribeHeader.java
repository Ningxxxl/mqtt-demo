package cn.ningxy.subscribe;

import cn.ningxy.base.MqttPackageFixedHeader;

/**
 * @author ningxy
 */
public class SubscribeHeader extends MqttPackageFixedHeader {
    private static final byte CONTROL_PACKET_TYPE_BITS = (byte) 0b1000_0010;

    private SubscribeHeader() {
    }

    public static SubscribeHeader of(int remainingLength) {
        SubscribeHeader subscribeHeader = new SubscribeHeader();
        subscribeHeader.setByte1(CONTROL_PACKET_TYPE_BITS);
        subscribeHeader.computeRemainingLengthBytes(remainingLength);
        return subscribeHeader;
    }
}
