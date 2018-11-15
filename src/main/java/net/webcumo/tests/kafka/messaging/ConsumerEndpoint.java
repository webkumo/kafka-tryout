package net.webcumo.tests.kafka.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.List;

public class ConsumerEndpoint extends AbstractConsumer {
    public ConsumerEndpoint(KafkaMessageListenerContainer<String, String> requestListenerContainer) {
        super(requestListenerContainer);
    }

    @Override
    public void onMessage(List<ConsumerRecord<String, String>> consumerRecords) {
        consumerRecords.stream()
                .map(ConsumerRecord::value)
                .forEach(this::publishMessage);
    }

    private void publishMessage(String message) {
        System.out.println("Got message: " + message);
    }
}
