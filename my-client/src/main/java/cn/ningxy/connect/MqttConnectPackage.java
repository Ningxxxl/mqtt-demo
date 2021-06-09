package cn.ningxy.connect;

import cn.ningxy.base.MqttPackage;
import cn.ningxy.base.MqttPackageFixedHeader;
import cn.ningxy.base.MqttPackagePayload;
import cn.ningxy.base.MqttPackageVariableHeader;

/**
 * @author ningxy
 */
public class MqttConnectPackage extends MqttPackage {

    protected MqttConnectPackage(MqttPackageFixedHeader fixedHeader,
                                 MqttPackageVariableHeader variableHeader,
                                 MqttPackagePayload payload) {
        super(fixedHeader, variableHeader, payload);
    }

    public static MqttConnectPackage create() {
        MqttPackageVariableHeader variableHeader = ConnectVariableHeader.ofDefault();
        MqttPackagePayload payload = ClientID.ofRandom();
        MqttPackageFixedHeader fixedHeader = ConnectHeader.of(variableHeader, payload);
        return new MqttConnectPackage(fixedHeader, variableHeader, payload);
    }
}
