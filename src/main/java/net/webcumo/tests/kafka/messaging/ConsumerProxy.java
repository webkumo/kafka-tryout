package net.webcumo.tests.kafka.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

public class ConsumerProxy extends AbstractConsumer {
    private final KafkaTemplate<String, String> responseTemplate;
    private final String defaultTopic;

    public ConsumerProxy(KafkaMessageListenerContainer<String, String> requestListenerContainer,
                         KafkaTemplate<String, String> responseTemplate,
                         String defaultTopic) {
        super(requestListenerContainer);
        this.responseTemplate = responseTemplate;
        this.defaultTopic = defaultTopic;
    }

    @Override
    public void onMessage(List<ConsumerRecord<String, String>> consumerRecords) {
        consumerRecords.forEach(this::processMessage);
    }

    private void processMessage(ConsumerRecord<String, String> record) {
        CalculatableMessage message = map(record.value());
        String response, topic;
        if (message == null) {
            response = "null";
            topic = defaultTopic;
        } else {
            response = message.getMessage() + "-=-" + message.getMessage().length();
            topic = message.getResponseTopic();
        }
        CalculatableMessage out = new CalculatableMessage();
        out.setMessage(response);
        ListenableFuture<SendResult<String, String>> result =
                responseTemplate.send(topic, record.key(), map(out));
        result.addCallback(new LoggingCallback<>());
    }
}
