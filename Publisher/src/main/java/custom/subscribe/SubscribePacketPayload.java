package custom.subscribe;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;

/**
 * @author ningxy
 */
public class SubscribePacketPayload {
    private byte[] topicLengthBytes = new byte[2];
    private String topic;
    private byte qos;

    public SubscribePacketPayload(String topic, byte qos) {
        this.topic = topic;
        this.topicLengthBytes = Shorts.toByteArray((short) topic.length());
        this.qos = qos;
    }

    public static SubscribePacketPayload of(String topic, byte qos) {
        return new SubscribePacketPayload(topic, qos);
    }

    public byte[] serializeToByteArray() {
        return Bytes.concat(topicLengthBytes, topic.getBytes(), new byte[]{(byte) (qos & 0b000000_11)});
    }
}
