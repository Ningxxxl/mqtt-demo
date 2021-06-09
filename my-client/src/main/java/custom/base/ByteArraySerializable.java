package custom.base;

import com.google.common.io.BaseEncoding;

/**
 * @author ningxy
 */
public interface ByteArraySerializable {
    /**
     * 序列化为 byteArray
     *
     * @return ByteArray
     */
    byte[] toByteArray();

    /**
     * 获取 ByteArray 长度
     *
     * @return ByteArray 长度
     */
    default int getByteArrayLength() {
        return toByteArray().length;
    }

    /**
     * 将 ByteArray 序列化为16进制字符串
     *
     * @return 16进制字符串
     */
    default String toHexString() {
        return BaseEncoding.base16().encode(toByteArray());
    }
}
