package custom;

import com.google.common.io.BaseEncoding;
import custom.base.PackageHandler;
import custom.connect.MqttConnectSegment;
import custom.publish.MqttPublishSegment;
import custom.subscribe.MqttSubscribeSegment;
import custom.subscribe.SubscribeHeader;
import custom.subscribe.SubscribePacketPayload;
import custom.subscribe.SubscribeVariableHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ningxy
 */
public class SimpleSubscriber {
    private static String hostName = "localhost";
    private static int hostPort = 1883;
    private static final String TOPIC = "default/topic";

    public static void main(String[] args) throws IOException {
        try (
                Socket socket = new Socket(hostName, hostPort);
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
        ) {
            sendRequest(os, MqttConnectSegment.create().toByteArray());
            byte[] connectResp = readResponse(is);
//            System.out.println("CONNECT RESPONSE [CONNACK] [HEX] => " + BaseEncoding.base16().encode(connectResp));

            sendRequest(os, MqttSubscribeSegment.create(TOPIC, (byte) 1).toByteArray());
            byte[] subscribeResp = readResponse(is);
//            System.out.println("CONNECT RESPONSE [PUBACK]  [HEX] => " + BaseEncoding.base16().encode(subscribeResp));

            while (true) {
                readResponse(is);
            }
        }
    }

    private static byte[] readResponse(InputStream is) throws IOException {
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
        PackageHandler.resolve(respBytes);
        return respBytes;
    }

    private static void sendRequest(OutputStream os, byte[] dataBytes) throws IOException {
        os.write(dataBytes);
        os.flush();
    }
}
