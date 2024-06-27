package com.vpactually.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.messaging.kafka.publishers.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantCreateCommandHandler {

    private final KafkaPublisher publisher;
    private final ObjectMapper om;

    public void handle(Order order) {
        try {
            publisher.publishOrderStatusFromRestaurantService(om.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
