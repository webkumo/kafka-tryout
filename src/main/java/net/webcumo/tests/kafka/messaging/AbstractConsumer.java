package net.webcumo.tests.kafka.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.io.IOException;

@Slf4j
abstract class AbstractConsumer implements BatchMessageListener<String, String> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static CalculatableMessage map(String value) {
        try {
            return MAPPER.readValue(value, CalculatableMessage.class);
        } catch (IOException e) {
            log.warn("Cannot convert data: ", e);
            return null;
        }
    }

    static String map(CalculatableMessage message) {
        try {
            return MAPPER.writeValueAsString(message);
        } catch (IOException e) {
            log.warn("Cannot convert data: ", e);
            return null;
        }
    }

    AbstractConsumer(KafkaMessageListenerContainer<String, String> requestListenerContainer) {
        requestListenerContainer.setupMessageListener(this);
        requestListenerContainer.start();
    }
}
