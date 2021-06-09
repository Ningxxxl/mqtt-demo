package cn.ningxy.subscribe;

import cn.ningxy.base.MqttPackage;
import cn.ningxy.base.MqttPackageFixedHeader;
import cn.ningxy.base.MqttPackagePayload;
import cn.ningxy.base.MqttPackageVariableHeader;
import cn.ningxy.connect.ConnectHeader;

/**
 * @author ningxy
 */
public class MqttSubscribePackage extends MqttPackage {
    public MqttSubscribePackage(MqttPackageFixedHeader fixedHeader, MqttPackageVariableHeader variableHeader, MqttPackagePayload payload) {
        super(fixedHeader, variableHeader, payload);
    }

    public static MqttSubscribePackage create(String topicName, byte qos) {
        MqttPackageVariableHeader variableHeader = SubscribeVariableHeader.ofRandom();
        MqttPackagePayload payload = SubscribePacketPayload.of(topicName, (byte) 0);
        MqttPackageFixedHeader fixedHeader = ConnectHeader.of(variableHeader, payload);

        return new MqttSubscribePackage(fixedHeader, variableHeader, payload);
    }
}
