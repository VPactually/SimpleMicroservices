package com.vpactually.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.dto.OrderDTO;
import com.vpactually.entities.Order;
import com.vpactually.entities.OrderStatus;
import com.vpactually.repositories.OrderRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderRepository repository;
    private final ObjectMapper om;
    private Order backupOrder = null;

    @Autowired
    public OrderService(KafkaTemplate<String, String> kafkaTemplate, OrderRepository repository, ObjectMapper om) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
        this.om = om;
    }

    @SneakyThrows
    public void createOrder(CreateOrderDTO createOrderDTO) {
        var order = Order.builder()
                .productIds(createOrderDTO.getProductIds())
                .userId(createOrderDTO.getUserId())
                .address(createOrderDTO.getAddress())
                .price(createOrderDTO.getPrice())
                .status(OrderStatus.CREATED)
                .build();
        repository.save(order);
        backupOrder = order;
        kafkaTemplate.send("order", om.writeValueAsString(order));
    }

    public OrderDTO getOrderById(Integer id) {
        return repository.findById(id)
                .map(OrderDTO::new)
                .orElseThrow();
    }


    @SneakyThrows
    @KafkaListener(topics = {"order_payment", "order_confirmed_by_rest", "order_delivery"},
            groupId = "order_service_group")
    public void updateOrderStatus(String message) {
        System.out.println("Message in OrderService (order_payment): " + message);
        var order = om.readValue(message, Order.class);
        repository.save(order);
        switch (order.getStatus()) {
            case PAYMENT_SUCCESS:
                kafkaTemplate.send("paid_order", om.writeValueAsString(order));
                break;
            case RESTAURANT_SUCCESS:
                kafkaTemplate.send("confirmed_order", om.writeValueAsString(order));
                break;
            default:
                kafkaTemplate.send("failed_order", om.writeValueAsString(backupOrder));
                break;
        }

    }

    public void deleteOrder(Integer id) {
        repository.deleteById(id);
    }

}

