package custom;

import java.util.Arrays;

/**
 * MQTT控制报文的类型
 *
 * @author ningxy
 */
public enum ControlPacketTypeEnum {
    // 控制报文的类型
    CONNECT("CONNECT", 1, "客户端请求连接服务端"),
    CONNACK("CONNACK", 2, "连接报文确认"),
    PUBLISH("PUBLISH", 3, "发布消息"),
    PUBACK("PUBACK", 4, "QoS 1消息发布收到确认"),
    PUBREC("PUBREC", 5, "发布收到（保证交付第一步）"),
    PUBREL("PUBREL", 6, "发布释放（保证交付第二步）"),
    PUBCOMP("PUBCOMP", 7, "QoS 2消息发布完成（保证交互第三步）"),
    SUBSCRIBE("SUBSCRIBE", 8, "客户端订阅请求"),
    SUBACK("SUBACK", 9, "订阅请求报文确认"),
    UNSUBSCRIBE("UNSUBSCRIBE", 10, "客户端取消订阅请求"),
    UNSUBACK("UNSUBACK", 11, "取消订阅报文确认"),
    PINGREQ("PINGREQ", 12, "心跳请求"),
    PINGRESP("PINGRESP", 13, "心跳响应"),
    DISCONNECT("DISCONNECT", 14, "客户端断开连接"),
    ;

    private final String type;
    private final Integer code;
    private final String message;

    ControlPacketTypeEnum(String type, Integer code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public static ControlPacketTypeEnum getByCode(int code) {
        return Arrays.stream(ControlPacketTypeEnum.values())
                .filter(type -> type.code == code)
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
