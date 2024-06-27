package com.vpactually.messaging.publishers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderCreated(String message) {
        kafkaTemplate.send("order_created", message);
    }

    public void publishOrderPaid(String message) {
        kafkaTemplate.send("paid_order", message);
    }

    public void publishConfirmedOrder(String message) {
        kafkaTemplate.send("confirmed_order", message);
    }
}
