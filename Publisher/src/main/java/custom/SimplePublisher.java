package custom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author ningxy
 */
public class SimplePublisher {
    private static String hostName = "localhost";
    private static int hostPort = 1883;

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket(hostName, hostPort);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();
        ) {
            sendRequest(os, MqttConnectSegment.create().toByteArray());
            String response = readResponse(is);
            System.out.println("res1 >>> \n" + response);

            sendRequest(os, MqttPublishSegment.create("default/topic", "lalala").toByteArray());
//            response = readResponse(is);
//            System.out.println("res2 >>> \n" + response);
        }
    }

    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count = 0;
        do {
            count = is.read(buffer);
            if (count != -1) {
                bos.write(buffer, 0, count);
            }
        } while (is.available() != 0);
        return bos.toString(StandardCharsets.UTF_8.name());
    }

    private static void sendRequest(OutputStream os, byte[] dataBytes) throws IOException {
        os.write(dataBytes);
        os.flush();
    }
}
