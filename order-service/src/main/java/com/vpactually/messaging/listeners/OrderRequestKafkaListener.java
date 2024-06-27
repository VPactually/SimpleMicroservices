package com.vpactually.messaging.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.services.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRequestKafkaListener {

    private final OrderApplicationService service;
    private final ObjectMapper om;

    @KafkaListener(topics = {"order_status_from_payment_service", "order_status_from_restaurant_service",
            "order_status_from_delivery_service"}, groupId = "order_service_group")
    public void listen(String message) {
        var order = getMessageAsOrderClass(message);
        service.update(order);
    }

    private Order getMessageAsOrderClass(String message) {
        System.out.println("Message in OrderService (order): " + message);
        try {
            return om.readValue(message, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
