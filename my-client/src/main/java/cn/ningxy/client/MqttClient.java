package cn.ningxy.client;

import cn.ningxy.base.ResponsePackageHandler;

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
    private String topicName = "default/topic";

    public MqttClient(String hostName, int hostPort, String topicName) {
        this.hostName = hostName;
        this.hostPort = hostPort;
        this.topicName = topicName;
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
        byte[] respBytes = bos.toByteArray();
        ResponsePackageHandler.resolve(respBytes);
        return respBytes;
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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
