package custom;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

import java.util.UUID;

/**
 * @author ningxy
 */
public class ClientID {
    private static final Short LENGTH;
    private static final String ID;

    static {
        ID = UUID.randomUUID().toString();
        LENGTH = (short) ID.length();
    }

    public static byte[] toByteArray() {
        return Bytes.concat(Shorts.toByteArray(LENGTH), ID.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(BaseEncoding.base16().encode(toByteArray()));
    }
}
