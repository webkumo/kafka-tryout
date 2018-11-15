package net.webcumo.tests.kafka;

import lombok.extern.slf4j.Slf4j;
import net.webcumo.tests.kafka.messaging.ConsumerEndpoint;
import net.webcumo.tests.kafka.messaging.ConsumerProxy;
import net.webcumo.tests.kafka.messaging.KafkaConfig;
import net.webcumo.tests.kafka.messaging.ProducerService;
import net.webcumo.tests.kafka.messaging.ServiceMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class KafkaService {
    private final ConsumerProxy proxy;
    private final ConsumerEndpoint consumer;
    private final ProducerService producer;

    public KafkaService(KafkaConfig config) {
        if (config.getMode() == ServiceMode.proxy) {
            this.proxy = new ConsumerProxy(config.getListener(), config.getTemplate(), config.getResponseTopic());
            this.consumer = null;
            this.producer = null;
        } else if (config.getMode() == ServiceMode.producer) {
            this.proxy = null;
            this.consumer = new ConsumerEndpoint(config.getListener());
            this.producer = new ProducerService(config.getTemplate(), config.getRequestTopic());
        } else {
            System.err.println("Error: no service mode were selected");
            throw new IllegalStateException("Error: no service mode were selected");
        }
    }

    @PostConstruct
    public void logState() {
        log.debug("Current config:\nproxy: {0}\nconsumer: {1}\nproducer: {2}", proxy, consumer, producer);
    }
}
