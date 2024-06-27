package com.vpactually.messaging.kafka.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.handlers.RestaurantCreateCommandHandler;
import com.vpactually.services.RestaurantApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantRequestKafkaListener {

    private final RestaurantCreateCommandHandler handler;
    private final RestaurantApplicationService service;
    private final ObjectMapper om;


    @KafkaListener(topics = "order_status_from_payment_service", groupId = "restaurant_service_group")
    public void listen(String message) {
        var order = getMessageAsOrderClass(message);
        service.checkOrder(order);
    }

    private Order getMessageAsOrderClass(String message) {
        System.out.println("Message in RestaurantService (order): " + message);
        try {
            return om.readValue(message, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
