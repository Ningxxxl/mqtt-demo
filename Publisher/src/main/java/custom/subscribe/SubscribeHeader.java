package custom.subscribe;

import custom.base.FixedHeader;

/**
 * @author ningxy
 */
public class SubscribeHeader extends FixedHeader {
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
