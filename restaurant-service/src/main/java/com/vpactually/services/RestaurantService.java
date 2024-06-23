package com.vpactually.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.entities.OrderStatus;
import com.vpactually.entities.Product;
import com.vpactually.repositories.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProductRepository repository;
    private final ObjectMapper om;

    @Autowired
    public RestaurantService(KafkaTemplate<String, String> kafkaTemplate, ProductRepository repository, ObjectMapper om) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
        this.om = om;
    }

    public void create(Product product) {
        repository.save(product);
    }

    @SneakyThrows
    @KafkaListener(topics = "paid_order", groupId = "paid_order_service_group")
    public void checkProducts(String message) {
        System.out.println("Message in RestaurantService (paid_order): " + message);
        var order = om.readValue(message, Order.class);
        var restProducts = repository.findAll();

        var restProductIds = restProducts
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getQuantity));
        var orderProducts = order.getProductIds()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        var resultOfCheck = orderProducts.entrySet().stream()
                .allMatch(entry -> restProductIds.containsKey(entry.getKey())
                        && restProductIds.get(entry.getKey()) >= entry.getValue());

        if (resultOfCheck) {
            restProducts.forEach(product -> product.setQuantity(
                    product.getQuantity() -  orderProducts.get(product.getId()).intValue()));
            repository.saveAll(restProducts);
            order.setStatus(OrderStatus.RESTAURANT_SUCCESS);
            kafkaTemplate.send("order_confirmed_by_rest", om.writeValueAsString(order));
        } else {
            order.setStatus(OrderStatus.RESTAURANT_FAILED);
            kafkaTemplate.send("order_confirmed_by_rest", om.writeValueAsString(order));
        }
    }
}
