package com.vpactually.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.messaging.kafka.publisher.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCreateCommandHandler {

    private final KafkaPublisher publisher;
    private final ObjectMapper om;

    public void handle(Order order) {
        try {
            publisher.publishOrderStatusFromDeliveryService(om.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
