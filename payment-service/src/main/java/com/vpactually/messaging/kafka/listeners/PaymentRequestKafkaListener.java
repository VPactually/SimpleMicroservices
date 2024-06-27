package com.vpactually.messaging.kafka.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.services.PaymentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentRequestKafkaListener {

    private final PaymentApplicationService service;
    private final ObjectMapper om;

    @KafkaListener(topics = "order_created", groupId = "payment_service_group")
    public void listen(String message) {
        var order = getMessageAsOrderClass(message);
        service.payOrder(order);
    }

    private Order getMessageAsOrderClass(String message) {
        System.out.println("Message in PaymentService (order): " + message);
        try {
            return om.readValue(message, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
