import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
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

    public static void main(String[] args) throws InterruptedException {
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
    }
}
