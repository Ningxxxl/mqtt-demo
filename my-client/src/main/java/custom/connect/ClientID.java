package custom.connect;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;
import custom.base.MqttPackagePayload;

import java.util.UUID;

/**
 * @author ningxy
 */
public class ClientID implements MqttPackagePayload {
    private final Short LENGTH;
    private final String ID;

    private ClientID() {
        ID = UUID.randomUUID().toString();
        LENGTH = (short) ID.length();
    }

    public static ClientID ofRandom() {
        return new ClientID();
    }

    @Override
    public byte[] toByteArray() {
        return Bytes.concat(Shorts.toByteArray(LENGTH), ID.getBytes());
    }
}
