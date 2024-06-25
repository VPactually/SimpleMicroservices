package com.vpactually.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Account;
import com.vpactually.entities.Order;
import com.vpactually.entities.OrderStatus;
import com.vpactually.repositories.AccountRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AccountRepository repository;
    private final ObjectMapper om;

    @Autowired
    public AccountService(KafkaTemplate<String, String> kafkaTemplate, AccountRepository repository, ObjectMapper om) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
        this.om = om;
    }

    public Boolean process(Integer userId, Integer amount) {
        var account = repository.findByUserId(userId)
                .orElseThrow();
        if (account.getBalance() < amount) {
            return false;
        }
        account.setBalance(account.getBalance() - amount);
        repository.save(account);
        return true;
    }

    @SneakyThrows
    @KafkaListener(topics = "order", groupId = "order_service_group")
    public void process(String message) {
        var order = getMessageAsOrderClass(message);
        order.setStatus(process(order.getUserId(), order.getPrice()) ?
                OrderStatus.PAYMENT_SUCCESS : OrderStatus.PAYMENT_FAILED);
        kafkaTemplate.send("order_payment", om.writeValueAsString(order));
    }

    @SneakyThrows
    @KafkaListener(topics = "failed_order", groupId = "failed_order_payment_service")
    public void rollback(String message) {
        var backupOder = getMessageAsOrderClass(message);
        add(backupOder.getUserId(), backupOder.getPrice());
    }

    public Boolean add(Integer userId, Integer amount) {
        var account = repository.findByUserId(userId)
                .orElseThrow();
        account.setBalance(account.getBalance() + amount);
        repository.save(account);
        return true;
    }

    public Boolean createAccount(Integer userId) {
        repository.save(new Account(null, userId, 0));
        return true;
    }

    public Account getAccount(Integer userId) {
        return repository.findByUserId(userId)
                .orElseThrow();
    }

    private Order getMessageAsOrderClass(String message) throws JsonProcessingException {
        System.out.println("Message in AccountService (order): " + message);
        return om.readValue(message, Order.class);
    }
}
