package com.vpactually.services;

import com.vpactually.entities.Order;
import com.vpactually.repositories.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDomainService {

    private final OrderRepository repository;
    @Getter
    private Order backupOrder = null;

    public void save(Order order) {
        repository.save(order);
        backupOrder = order;
    }

    public Order getOrderById(Integer id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public void update(Order order) {
        repository.save(order);
    }

    public void deleteOrder(Integer id) {
        repository.deleteById(id);
    }
}

