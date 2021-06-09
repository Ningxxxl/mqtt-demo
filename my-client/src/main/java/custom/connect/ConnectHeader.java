package custom.connect;

import custom.base.MqttPackageFixedHeader;

/**
 * @author ningxy
 */
public class ConnectHeader extends MqttPackageFixedHeader {
    private static final byte CONTROL_PACKET_TYPE_BITS = 0b0001_0000;

    private ConnectHeader() {
    }

    public static ConnectHeader of(int remainingLength) {
        ConnectHeader connectHeader = new ConnectHeader();
        connectHeader.setByte1(CONTROL_PACKET_TYPE_BITS);
        connectHeader.computeRemainingLengthBytes(remainingLength);
        return connectHeader;
    }
}
