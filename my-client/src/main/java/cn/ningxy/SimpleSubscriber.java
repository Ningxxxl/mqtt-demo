package cn.ningxy;

import cn.ningxy.connect.MqttConnectPackage;
import cn.ningxy.subscribe.MqttSubscribePackage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author ningxy
 */
public class SimpleSubscriber extends MqttClient {
    private String topicName = "default/topic";

    public SimpleSubscriber(String hostName, int hostPort, String topicName) {
        super(hostName, hostPort);
        this.topicName = topicName;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("参数 <host> <port> <topic>");
            throw new IllegalArgumentException("参数有误");
        }
        SimpleSubscriber simpleSubscriber = new SimpleSubscriber(args[0], Integer.parseInt(args[1]), args[2]);
        simpleSubscriber.work();
    }

    @Override
    protected void work() throws Exception {
        try (
                Socket socket = new Socket(getHostName(), getHostPort());
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
        ) {
            sendRequest(os, MqttConnectPackage.create().toByteArray());
            readResponse(is);

            sendRequest(os, MqttSubscribePackage.create(topicName, (byte) 1).toByteArray());
            readResponse(is);

            while (true) {
                readResponse(is);
            }
        }
    }
}
