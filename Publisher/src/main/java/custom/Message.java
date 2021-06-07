package custom;

/**
 * @author ningxy
 */
public class Message {
    private String message;

    private Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }

    public byte[] serializeToByteArray() {
        return this.message.getBytes();
    }
}
