package cn.ningxy.publish;

import cn.ningxy.base.MqttPackageFixedHeader;
import cn.ningxy.base.MqttPackagePayload;
import cn.ningxy.base.MqttPackageVariableHeader;

/**
 * @author ningxy
 */
public class PublishHeader extends MqttPackageFixedHeader {
    private static final byte CONTROL_PACKET_TYPE_BITS = 0b0011_0000;
    private static final byte DUP_BIT_MASK = 0b0000_1_000;
    private static final byte[] QOS_TYPE_BITS = new byte[]{0b00000_00_0, 0b00000_01_0, 0b00000_10_0};
    private static final byte RETAIN_BIT_MASK = 0b0000000_1;
    private byte qosType = 0;
    private boolean isRetain;

    public PublishHeader(byte qosType, boolean isRetain) {
        this.qosType = qosType;
        this.isRetain = isRetain;
    }

    public static PublishHeader of(MqttPackageVariableHeader variableHeader,
                                   MqttPackagePayload payload,
                                   byte qosType,
                                   boolean isRetain) {
        int remainingLength = variableHeader.getByteArrayLength() + payload.getByteArrayLength();
        PublishHeader publishHeader = new PublishHeader(qosType, isRetain);
        publishHeader.computeRemainingLengthBytes(remainingLength);
        byte byte1 = (byte) (CONTROL_PACKET_TYPE_BITS | QOS_TYPE_BITS[qosType]);
        if (isRetain) {
            byte1 = (byte) (byte1 | RETAIN_BIT_MASK);
        }
        publishHeader.setByte1(byte1);
        return publishHeader;
    }

    public byte getQosType() {
        return qosType;
    }

    public void setQosType(byte qosType) {
        this.qosType = qosType;
    }
}
