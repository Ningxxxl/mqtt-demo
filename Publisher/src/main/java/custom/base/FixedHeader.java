package custom.base;

import com.google.common.primitives.Bytes;

/**
 * @author ningxy
 */
public class FixedHeader {
    private byte byte1;
    private byte[] remainingLengthBytes;

    protected FixedHeader() {
    }

    public byte[] toByteArray() {
        return Bytes.concat(new byte[]{byte1}, remainingLengthBytes);
    }

    protected void computeRemainingLengthBytes(int len) {
        int size = 0;
        for (int t = len; t > 0; size++) {
            t = t >>> 7;
        }
        byte[] lenBytes = new byte[size];
        for (int i = 0; i < size; i++) {
            lenBytes[i] = (byte) (len & 0b0111_1111);
            len = len >>> 7;
            if (i != size - 1) {
                lenBytes[i] = (byte) (lenBytes[i] | 0b1000_0000);
            }
        }
        this.remainingLengthBytes = lenBytes;
    }

    public byte getByte1() {
        return byte1;
    }

    public void setByte1(byte byte1) {
        this.byte1 = byte1;
    }

    public byte[] getRemainingLengthBytes() {
        return remainingLengthBytes;
    }

    public void setRemainingLengthBytes(byte[] remainingLengthBytes) {
        this.remainingLengthBytes = remainingLengthBytes;
    }
}
