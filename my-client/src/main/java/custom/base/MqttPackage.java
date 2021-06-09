package custom.base;

import com.google.common.primitives.Bytes;

/**
 * @author ningxy
 */
public abstract class MqttPackage implements ByteArraySerializable {
    private MqttPackageFixedHeader fixedHeader;
    private MqttPackageVariableHeader variableHeader;
    private MqttPackagePayload payload;

    @Override
    public byte[] toByteArray() {
        return Bytes.concat(fixedHeader.toByteArray(), variableHeader.toByteArray(), payload.toByteArray());
    }

    public int getRemainingLength() {
        return this.variableHeader.getByteArrayLength() + this.payload.getByteArrayLength();
    }

    public void setFixedHeader(MqttPackageFixedHeader fixedHeader) {
        this.fixedHeader = fixedHeader;
    }

    public void setVariableHeader(MqttPackageVariableHeader variableHeader) {
        this.variableHeader = variableHeader;
    }

    public void setPayload(MqttPackagePayload payload) {
        this.payload = payload;
    }
}
