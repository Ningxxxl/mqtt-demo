import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author ningxy
 */
public class SubscriberClient {
    private static String SERVER_HOST = "127.0.0.1";
    private static int SERVER_PORT = 1883;
    private static String TOPIC = "default/topic";

    private static Mqtt3AsyncClient client;
    private static final Consumer<Mqtt3Publish> RECEIVE_CALLBACK;

    static {
        RECEIVE_CALLBACK = mqtt3Publish -> {
            System.out.println(mqtt3Publish);
            String payloadStr = new String(mqtt3Publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
            System.out.println("Msg = " + payloadStr);
        };
    }

    public static void main(String[] args) {
        if (args.length >= 3) {
            SERVER_HOST = args[0];
            SERVER_PORT = Integer.parseInt(args[1]);
            TOPIC = args[2];
        }
        configClient();

        client.connect();
        client.subscribeWith()
                .topicFilter(TOPIC)
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(RECEIVE_CALLBACK)
                .send();
    }

    private static void configClient() {
        client = Mqtt3Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(SERVER_HOST)
                .serverPort(SERVER_PORT)
                .addConnectedListener(context -> System.out.println("connected"))
                .addDisconnectedListener(context -> System.out.println("disconnected"))
                .buildAsync();
        Runtime.getRuntime().addShutdownHook(GracefulExit.of(client));
    }

    static class GracefulExit extends Thread {
        private final Mqtt3AsyncClient client;

        public static GracefulExit of(Mqtt3AsyncClient client) {
            return new GracefulExit(client);
        }

        public GracefulExit(Mqtt3AsyncClient client) {
            this.client = client;
        }

        @Override
        public void run() {
            System.out.println("\nTry to exit graceful...");

            if (!MqttClientState.DISCONNECTED.equals(client.getState())) {
                CompletableFuture<Void> future = client.disconnect();
                while (!future.isDone() && !future.isCancelled()) {};
            }

            System.out.println("Client Status: " + client.getState());
            System.out.println("\n👋🏻Bye!");
        }
    }
}
