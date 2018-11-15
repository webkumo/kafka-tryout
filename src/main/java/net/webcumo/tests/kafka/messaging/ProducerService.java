package net.webcumo.tests.kafka.messaging;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class ProducerService implements Runnable {
    private final KafkaTemplate<String, String> template;
    private final BufferedReader reader;
    private final String responseTopic;

    public ProducerService(KafkaTemplate<String, String> template, String responseTopic) {
        this.template = template;
        this.responseTopic = responseTopic;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            readAndSend();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                break;
            }
        }
    }

    private void readAndSend() {
        try {
            String value = reader.readLine();
            CalculatableMessage message = new CalculatableMessage();
            message.setMessage(value);
            message.setResponseTopic(responseTopic);
            String topic = value.startsWith("1") ? "1" : "2";
            template.send(topic, UUID.randomUUID().toString(), AbstractConsumer.map(message));
        } catch (IOException ignore) {}
    }
}
