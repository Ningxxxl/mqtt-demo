package cn.ningxy.client;

import cn.ningxy.publish.MqttPublishPackage;
import com.google.common.io.BaseEncoding;
import cn.ningxy.connect.MqttConnectPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ningxy
 */
public class SimplePublisher extends MqttClient {

    public SimplePublisher(String hostName, int hostPort, String topicName) {
        super(hostName, hostPort, topicName);
    }

    @Override
    public void work() throws IOException {
        try (
                Socket socket = new Socket(getHostName(), getHostPort());
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                Scanner scanner = new Scanner(System.in);
        ) {
            sendRequest(os, MqttConnectPackage.create().toByteArray());
            readResponse(is);

            System.out.print(">>> ");
            while (scanner.hasNext()) {
                System.out.print(">>> ");
                String s = scanner.nextLine();
                if ("exit".equalsIgnoreCase(s)) {
                    break;
                }
                MqttPublishPackage mqttPublishPackage = MqttPublishPackage.create(getTopicName(), s);
                sendRequest(os, mqttPublishPackage.toByteArray());
            }
        }
    }
}
