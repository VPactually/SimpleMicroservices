package com.vpactually.deliveryservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.function.Function;
import java.util.stream.Collectors;

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

        var order = getMessageAsOrderClass(message);
        order.setStatus(OrderStatus.DELIVERY_IN_PROCESS);
        var delivery = new Delivery(order);
        repository.save(delivery);
        kafkaTemplate.send("order_delivery", om.writeValueAsString(order));
        Thread.sleep(60000);
        order.setStatus(OrderStatus.DELIVERY_SUCCESS);
        repository.save(delivery);
        kafkaTemplate.send("order_delivery", om.writeValueAsString(order));
    }

    @SneakyThrows
    @KafkaListener(topics = "failed_order", groupId = "failed_order_delivery_service")
    public void rollback(String message) {
        var backupOder = getMessageAsOrderClass(message);
        backupOder.setStatus(OrderStatus.DELIVERY_FAILED);
        var delivery = new Delivery(backupOder);
        repository.save(delivery);
    }

    private Order getMessageAsOrderClass(String message) throws JsonProcessingException {
        System.out.println("Message in AccountService (order): " + message);
        return om.readValue(message, Order.class);
    }
}
