import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author ningxy
 */
public class PublisherClient {
    private static String SERVER_HOST = "127.0.0.1";
    private static int SERVER_PORT = 1883;

    private static Mqtt3AsyncClient client;

    public static void main(String[] args) {
        if (args.length >= 2) {
            SERVER_HOST = args[0];
            SERVER_PORT = Integer.parseInt(args[1]);
        }
        configClient();

        CompletableFuture<Mqtt3ConnAck> connect = client.connect();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            if ("exit".equalsIgnoreCase(s)) {
                break;
            }
            connect.thenCompose(connAck -> sendMsg(s));
        }

        client.disconnect();
    }

    private static void configClient() {
        client = Mqtt3Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(SERVER_HOST)
                .serverPort(SERVER_PORT)
                .addConnectedListener(context -> System.out.println("connected"))
                .addDisconnectedListener(context -> System.out.println("disconnected"))
                .buildAsync();
    }

    private static CompletableFuture<Mqtt3Publish> sendMsg(String payloadString) {
        return client.publishWith()
                .topic("test/topic")
                .payload(payloadString.getBytes())
                .retain(true)
                .send();
    }
}
