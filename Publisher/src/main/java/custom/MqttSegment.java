package custom;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;

/**
 * @author ningxy
 */
public class MqttSegment {
    private byte[] fixedHeader;
    private byte[] variableHeader;
    private byte[] payload;

    public byte[] serializeToByteArray() {
        return Bytes.concat(fixedHeader, variableHeader, payload);
    }

    public static void main(String[] args) {
        byte[] bytes1 = createConnectionSeg();
        byte[] bytes2 = createPublishSeg("asd");
        System.out.println(BaseEncoding.base16().encode(bytes1));
        System.out.println(BaseEncoding.base16().encode(bytes2));
    }

    public int getRemainingLength() {
        return this.variableHeader.length + this.payload.length;
    }

    public static byte[] createConnectionSeg() {
        MqttSegment connectSegment = new MqttSegment();
        connectSegment.variableHeader = ConnectVariableHeader.toByteArray();
        connectSegment.payload = ClientID.toByteArray();
        connectSegment.fixedHeader = ConnectHeader.of(connectSegment.getRemainingLength()).toByteArray();

        return connectSegment.serializeToByteArray();
    }

    public static byte[] createPublishSeg(String msg) {
        String topicName = "default/topic";
        MqttSegment publishSegment = new MqttSegment();
        publishSegment.variableHeader = PublishVariableHeader.of(topicName, (byte) 0).serializeToByteArray();
        publishSegment.payload = Message.of(msg).serializeToByteArray();
        publishSegment.fixedHeader = PublishHeader.of(publishSegment.getRemainingLength(), (byte) 0, true).toByteArray();

        return publishSegment.serializeToByteArray();
    }
}
