package cn.ningxy.publish;

import cn.ningxy.base.MqttPackagePayload;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;

import java.nio.charset.StandardCharsets;

/**
 * @author ningxy
 */
public class Message implements MqttPackagePayload {
    private final short length;
    private final String message;

    private Message(String message) {
        this.message = message;
        this.length = (short) message.getBytes(StandardCharsets.UTF_8).length;
    }

    public static Message of(String message) {
        return new Message(message);
    }

    @Override
    public byte[] toByteArray() {
        return Bytes.concat(Shorts.toByteArray(length), this.message.getBytes(StandardCharsets.UTF_8));
    }
}
