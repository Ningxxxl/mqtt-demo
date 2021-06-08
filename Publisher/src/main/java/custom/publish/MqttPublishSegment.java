package custom.publish;

import custom.base.MqttSegment;

/**
 * @author ningxy
 */
public class MqttPublishSegment extends MqttSegment {
    private MqttPublishSegment() {
    }

    public static MqttPublishSegment create(String topicName, String message) {
        MqttPublishSegment publishSegment = new MqttPublishSegment();

        publishSegment.setVariableHeader(PublishVariableHeader.of(topicName, (byte) 0).serializeToByteArray());
        publishSegment.setPayload(Message.of(message).serializeToByteArray());
        int remainingLength = publishSegment.getRemainingLength();
        publishSegment.setFixedHeader(PublishHeader.of(remainingLength, (byte) 0, true).toByteArray());

        return publishSegment;
    }
}
