package custom.publish;

import custom.base.MqttPackage;

/**
 * @author ningxy
 */
public class MqttPublishPackage extends MqttPackage {
    private MqttPublishPackage() {
    }

    public static MqttPublishPackage create(String topicName, String message) {
        MqttPublishPackage publishSegment = new MqttPublishPackage();

        publishSegment.setVariableHeader(PublishVariableHeader.of(topicName, (byte) 0));
        publishSegment.setPayload(Message.of(message));
        int remainingLength = publishSegment.getRemainingLength();
        publishSegment.setFixedHeader(PublishHeader.of(remainingLength, (byte) 0, true));

        return publishSegment;
    }
}
