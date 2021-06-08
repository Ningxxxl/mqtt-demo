package custom.base;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;

/**
 * @author ningxy
 */
public abstract class MqttPackage {
    private byte[] fixedHeader;
    private byte[] variableHeader;
    private byte[] payload;

    public byte[] toByteArray() {
        return Bytes.concat(fixedHeader, variableHeader, payload);
    }

    public int getRemainingLength() {
        return this.variableHeader.length + this.payload.length;
    }

    public String toHexString() {
        return BaseEncoding.base16().encode(toByteArray());
    }

    public byte[] getFixedHeader() {
        return fixedHeader;
    }

    public void setFixedHeader(byte[] fixedHeader) {
        this.fixedHeader = fixedHeader;
    }

    public byte[] getVariableHeader() {
        return variableHeader;
    }

    public void setVariableHeader(byte[] variableHeader) {
        this.variableHeader = variableHeader;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
