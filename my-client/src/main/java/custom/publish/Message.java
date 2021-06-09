package custom.publish;

import custom.base.MqttPackagePayload;

/**
 * @author ningxy
 */
public class Message implements MqttPackagePayload {
    private final String message;

    private Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }

    @Override
    public byte[] toByteArray() {
        return this.message.getBytes();
    }
}
