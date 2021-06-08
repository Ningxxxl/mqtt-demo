package custom.subscribe;

import custom.base.MqttSegment;

/**
 * @author ningxy
 */
public class MqttSubscribeSegment extends MqttSegment {
    private MqttSubscribeSegment() {
    }

    public static MqttSubscribeSegment create(String topicName, byte qos) {
        MqttSubscribeSegment subscribeSegment = new MqttSubscribeSegment();

        subscribeSegment.setVariableHeader(SubscribeVariableHeader.of().toByteArray());
        subscribeSegment.setPayload(SubscribePacketPayload.of(topicName, (byte) 0).serializeToByteArray());
        int remainingLength = subscribeSegment.getRemainingLength();
        subscribeSegment.setFixedHeader(SubscribeHeader.of(remainingLength).toByteArray());

        return subscribeSegment;
    }
}
