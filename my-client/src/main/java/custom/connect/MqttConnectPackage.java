package custom.connect;

import custom.base.MqttPackage;

/**
 * @author ningxy
 */
public class MqttConnectPackage extends MqttPackage {
    private MqttConnectPackage() {
    }

    public static MqttConnectPackage create() {
        MqttConnectPackage connectPackage = new MqttConnectPackage();

        connectPackage.setVariableHeader(ConnectVariableHeader.ofDefault());
        connectPackage.setPayload(ClientID.ofRandom());
        int remainingLength = connectPackage.getRemainingLength();
        connectPackage.setFixedHeader(ConnectHeader.of(remainingLength));

        return connectPackage;
    }
}
