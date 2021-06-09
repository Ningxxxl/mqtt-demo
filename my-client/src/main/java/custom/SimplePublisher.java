package custom;

import com.google.common.io.BaseEncoding;
import custom.connect.MqttConnectPackage;
import custom.publish.MqttPublishPackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ningxy
 */
public class SimplePublisher {
    private static String hostName = "localhost";
    private static int hostPort = 1883;

    public static void main(String[] args) throws IOException {

        try (
                Socket socket = new Socket(hostName, hostPort);
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

//            response = readResponse(is);
//            System.out.println("res2 >>> \n" + response);
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
        return bos.toByteArray();
    }

    private static void sendRequest(OutputStream os, byte[] dataBytes) throws IOException {
        os.write(dataBytes);
        os.flush();
    }
}
