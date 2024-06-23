package com.vpactually.deliveryservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.deliveryservice.entities.Delivery;
import com.vpactually.deliveryservice.entities.Order;
import com.vpactually.deliveryservice.entities.OrderStatus;
import com.vpactually.deliveryservice.repositories.DeliveryRepository;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DeliveryRepository repository;
    private final ObjectMapper om;

    @Autowired
    public DeliveryService(KafkaTemplate<String, String> kafkaTemplate, DeliveryRepository repository, ObjectMapper om) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
        this.om = om;
    }

    @SneakyThrows
    @KafkaListener(topics = "confirmed_order", groupId = "delivery_service_group")
    public void process(String message) {
        System.out.println("Message in DeliveryService (confirmed_order): " + message);
        var order = om.readValue(message, Order.class);
        var delivery = new Delivery(order);
        repository.save(delivery);
        order.setStatus(OrderStatus.DELIVERY_IN_PROCESS);
        System.out.println("Message before sending to delivery-service (order_delivery): " + om.writeValueAsString(order));
        kafkaTemplate.send("order_delivery", om.writeValueAsString(order));
        System.out.println("Message after sending to delivery-service (order_delivery): " + om.writeValueAsString(order));
        Thread.sleep(60000);
        order.setStatus(OrderStatus.DELIVERY_SUCCESS);
        System.out.println("Message before sending to final-service: " + om.writeValueAsString(order));
        kafkaTemplate.send("order_delivery", om.writeValueAsString(order));
    }
}
