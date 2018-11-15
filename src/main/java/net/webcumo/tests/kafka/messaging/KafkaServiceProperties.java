package net.webcumo.tests.kafka.messaging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka-service")
@Getter
@Setter
public class KafkaServiceProperties {
    private ServiceMode mode;
    private String requestTopic;
    private String answerTopic;
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer queueCapacity;
}
