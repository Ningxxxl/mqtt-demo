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
public class SimpleHttpClient {
    private static String hostName = "www.bjuidc.ncut.edu.cn";
    private static int hostPort = 80;
    private static String path = "/api/exhibition/show";

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket(hostName, hostPort);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();
        ) {
            sendRequest(os);
            String response = readResponse(is);
            System.out.println(response);
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

    private static void sendRequest(OutputStream os) throws IOException {
        String headers = generateRequestHeaders();
        byte[] headersBytes = headers.getBytes(StandardCharsets.UTF_8);

        os.write(headersBytes);
        os.flush();
    }

    private static String generateRequestHeaders() {
        return "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + hostName + "\r\n" +
                "Proxy-Connection: keep-alive\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36\r\n" +
                "Accept: application/json\r\n" +
                "\r\n";
    }
}