package cn.ningxy.subscribe;

import cn.ningxy.base.MqttPackagePayload;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;

/**
 * @author ningxy
 */
public class SubscribePacketPayload implements MqttPackagePayload {
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

    @Override
    public byte[] toByteArray() {
        return Bytes.concat(topicLengthBytes, topic.getBytes(), new byte[]{(byte) (qos & 0b000000_11)});
    }
}
