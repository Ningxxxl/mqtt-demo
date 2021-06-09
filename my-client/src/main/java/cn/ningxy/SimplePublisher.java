package cn.ningxy;

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

    public SimplePublisher(String hostName, int hostPort) {
        super(hostName, hostPort);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("参数 <host> <port>");
            throw new IllegalArgumentException("参数有误");
        }
        SimplePublisher simplePublisher = new SimplePublisher(args[0], Integer.parseInt(args[1]));
        simplePublisher.work();
    }

    @Override
    protected void work() throws IOException {
        try (
                Socket socket = new Socket(getHostName(), getHostPort());
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                Scanner scanner = new Scanner(System.in);
        ) {
            sendRequest(os, MqttConnectPackage.create().toByteArray());
            byte[] response = readResponse(is);
            System.out.println("CONNECT RESPONSE [HEX] => " + BaseEncoding.base16().encode(response));

            System.out.print(">>> ");
            while (scanner.hasNext()) {
                System.out.print(">>> ");
                String s = scanner.nextLine();
                if ("exit".equalsIgnoreCase(s)) {
                    break;
                }
                sendRequest(os, MqttPublishPackage.create("default/topic", s).toByteArray());
            }
        }
    }
}
