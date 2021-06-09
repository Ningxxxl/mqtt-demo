package custom.subscribe;

import custom.base.MqttPackage;

/**
 * @author ningxy
 */
public class MqttSubscribePackage extends MqttPackage {
    private MqttSubscribePackage() {
    }

    public static MqttSubscribePackage create(String topicName, byte qos) {
        MqttSubscribePackage subscribeSegment = new MqttSubscribePackage();

        subscribeSegment.setVariableHeader(SubscribeVariableHeader.ofRandom());
        subscribeSegment.setPayload(SubscribePacketPayload.of(topicName, (byte) 0));
        int remainingLength = subscribeSegment.getRemainingLength();
        subscribeSegment.setFixedHeader(SubscribeHeader.of(remainingLength));

        return subscribeSegment;
    }
}
