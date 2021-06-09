package custom;

import custom.base.ResponsePackageHandler;
import custom.connect.MqttConnectPackage;
import custom.subscribe.MqttSubscribePackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            sendRequest(os, MqttConnectPackage.create().toByteArray());
            readResponse(is);

            sendRequest(os, MqttSubscribePackage.create(TOPIC, (byte) 1).toByteArray());
            readResponse(is);

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
        ResponsePackageHandler.resolve(respBytes);
        return respBytes;
    }

    private static void sendRequest(OutputStream os, byte[] dataBytes) throws IOException {
        os.write(dataBytes);
        os.flush();
    }
}
