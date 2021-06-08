package custom.connect;

import custom.base.MqttPackage;

/**
 * @author ningxy
 */
public class MqttConnectPackage extends MqttPackage {
    private MqttConnectPackage() {
    }

    public static MqttConnectPackage create() {
        MqttConnectPackage connectSegment = new MqttConnectPackage();

        connectSegment.setVariableHeader(ConnectVariableHeader.toByteArray());
        connectSegment.setPayload(ClientID.toByteArray());
        int remainingLength = connectSegment.getRemainingLength();
        connectSegment.setFixedHeader(ConnectHeader.of(remainingLength).toByteArray());

        return connectSegment;
    }
}
