package cn.ningxy.subscribe;

import cn.ningxy.base.MqttPackageFixedHeader;
import cn.ningxy.base.MqttPackagePayload;
import cn.ningxy.base.MqttPackageVariableHeader;

/**
 * @author ningxy
 */
public class SubscribeHeader extends MqttPackageFixedHeader {
    private static final byte CONTROL_PACKET_TYPE_BITS = (byte) 0b1000_0010;

    private SubscribeHeader() {
    }

    public static SubscribeHeader of(MqttPackageVariableHeader variableHeader,
                                     MqttPackagePayload payload) {
        int remainingLength = variableHeader.getByteArrayLength() + payload.getByteArrayLength();
        SubscribeHeader subscribeHeader = new SubscribeHeader();
        subscribeHeader.setByte1(CONTROL_PACKET_TYPE_BITS);
        subscribeHeader.computeRemainingLengthBytes(remainingLength);
        return subscribeHeader;
    }
}
