package cn.ningxy.connect;

import cn.ningxy.base.MqttPackageFixedHeader;
import cn.ningxy.base.MqttPackagePayload;
import cn.ningxy.base.MqttPackageVariableHeader;

/**
 * @author ningxy
 */
public class ConnectHeader extends MqttPackageFixedHeader {
    private static final byte CONTROL_PACKET_TYPE_BITS = 0b0001_0000;

    private ConnectHeader() {
    }

    public static ConnectHeader of(MqttPackageVariableHeader variableHeader,
                                   MqttPackagePayload payload) {
        int remainingLength = variableHeader.getByteArrayLength() + payload.getByteArrayLength();
        ConnectHeader connectHeader = new ConnectHeader();
        connectHeader.setByte1(CONTROL_PACKET_TYPE_BITS);
        connectHeader.computeRemainingLengthBytes(remainingLength);
        return connectHeader;
    }
}
