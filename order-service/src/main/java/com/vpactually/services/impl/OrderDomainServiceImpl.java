package com.vpactually.services.impl;

import com.vpactually.entities.Order;
import com.vpactually.repositories.OrderRepository;
import com.vpactually.services.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final Logger log = LoggerFactory.getLogger(OrderDomainServiceImpl.class);
    private final OrderRepository repository;
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

    public Order getBackupOrder() {
        log.info("Getting backup order");
        return backupOrder;
    }
}

