package com.vpactually.messaging.kafka.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.services.DeliveryApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryRequestKafkaListener {

    private final DeliveryApplicationService service;
    private final ObjectMapper om;

    @KafkaListener(topics = "confirmed_order", groupId = "delivery_service_group")
    public void listen(String message) {
        var order = getMessageAsOrderClass(message);
        service.process(order);
    }

    private Order getMessageAsOrderClass(String message) {
        System.out.println("Message in DeliveryService (order): " + message);
        try {
            return om.readValue(message, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

