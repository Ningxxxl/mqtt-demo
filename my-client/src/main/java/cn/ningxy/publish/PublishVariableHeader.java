package cn.ningxy.publish;

import com.google.common.primitives.Bytes;
import cn.ningxy.base.MqttPackageVariableHeader;

/**
 * @author ningxy
 */
public class PublishVariableHeader implements MqttPackageVariableHeader {
    private String topicName;
    /**
     * 暂时无效，固定为0，太难写了...
     */
    private byte qosType = 0;

    private byte[] topicNameBytes;

    private PublishVariableHeader() {
    }

    private PublishVariableHeader(String topicName, byte qosType) {
        this.topicName = topicName;
        this.qosType = qosType;
        computeTopicNameBytes();
    }

    public static PublishVariableHeader of(String topicName, byte qosType) {
        return new PublishVariableHeader(topicName, qosType);
    }

    private void computeTopicNameBytes() {
        byte[] nameBytes = this.topicName.getBytes();
        int length = nameBytes.length;
        this.topicNameBytes = new byte[length + 2];
        System.arraycopy(nameBytes, 0, this.topicNameBytes, 2, length);
        topicNameBytes[0] = (byte) ((length >>> 8) & 0xFF);
        topicNameBytes[1] = (byte) (length & 0xFF);
    }

    @Override
    public byte[] toByteArray() {
        return Bytes.concat(topicNameBytes);
    }
}
