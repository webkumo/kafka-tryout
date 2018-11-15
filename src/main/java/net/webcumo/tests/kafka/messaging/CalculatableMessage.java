package net.webcumo.tests.kafka.messaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalculatableMessage implements Serializable {
    private String message;
    private String responseTopic;
}
