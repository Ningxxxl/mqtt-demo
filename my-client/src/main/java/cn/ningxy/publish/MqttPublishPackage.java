package cn.ningxy.publish;

import cn.ningxy.base.MqttPackage;
import cn.ningxy.base.MqttPackageFixedHeader;
import cn.ningxy.base.MqttPackagePayload;
import cn.ningxy.base.MqttPackageVariableHeader;
import cn.ningxy.connect.ConnectHeader;

/**
 * @author ningxy
 */
public class MqttPublishPackage extends MqttPackage {
    public MqttPublishPackage(MqttPackageFixedHeader fixedHeader,
                              MqttPackageVariableHeader variableHeader,
                              MqttPackagePayload payload) {
        super(fixedHeader, variableHeader, payload);
    }

    public static MqttPublishPackage create(String topicName, String message) {
        MqttPackageVariableHeader variableHeader = PublishVariableHeader.of(topicName, (byte) 0);
        MqttPackagePayload payload = Message.of(message);
        MqttPackageFixedHeader fixedHeader = PublishHeader.of(variableHeader, payload, (byte) 1, false);
        return new MqttPublishPackage(fixedHeader, variableHeader, payload);
    }
}
