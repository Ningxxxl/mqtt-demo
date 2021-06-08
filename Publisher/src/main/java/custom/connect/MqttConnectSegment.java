package custom.connect;

import custom.base.MqttSegment;

/**
 * @author ningxy
 */
public class MqttConnectSegment extends MqttSegment {
    private MqttConnectSegment() {
    }

    public static MqttConnectSegment create() {
        MqttConnectSegment connectSegment = new MqttConnectSegment();

        connectSegment.setVariableHeader(ConnectVariableHeader.toByteArray());
        connectSegment.setPayload(ClientID.toByteArray());
        int remainingLength = connectSegment.getRemainingLength();
        connectSegment.setFixedHeader(ConnectHeader.of(remainingLength).toByteArray());

        return connectSegment;
    }
}
