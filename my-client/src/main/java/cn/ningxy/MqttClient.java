package cn.ningxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ningxy
 */
public abstract class MqttClient {
    private String hostName = "localhost";
    private int hostPort = 1883;

    public MqttClient() {
    }

    public MqttClient(int hostPort) {
        this.hostPort = hostPort;
    }

    public MqttClient(String hostName, int hostPort) {
        this.hostName = hostName;
        this.hostPort = hostPort;
    }

    protected abstract void work() throws Exception;

    protected static byte[] readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count = 0;
        do {
            count = is.read(buffer);
            if (count != -1) {
                bos.write(buffer, 0, count);
            }
        } while (is.available() != 0);
        return bos.toByteArray();
    }

    protected static void sendRequest(OutputStream os, byte[] dataBytes) throws IOException {
        os.write(dataBytes);
        os.flush();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }
}
