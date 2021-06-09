package cn.ningxy.client;

import cn.ningxy.connect.MqttConnectPackage;
import cn.ningxy.subscribe.MqttSubscribePackage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author ningxy
 */
public class SimpleSubscriber extends MqttClient {

    public SimpleSubscriber(String hostName, int hostPort, String topicName) {
        super(hostName, hostPort, topicName);
    }

    @Override
    public void work() throws Exception {
        try (
                Socket socket = new Socket(getHostName(), getHostPort());
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
        ) {
            sendRequest(os, MqttConnectPackage.create().toByteArray());
            readResponse(is);

            sendRequest(os, MqttSubscribePackage.create(getTopicName(), (byte) 1).toByteArray());
            readResponse(is);

            while (true) {
                readResponse(is);
            }
        }
    }
}
