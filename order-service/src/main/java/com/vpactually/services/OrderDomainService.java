package com.vpactually.services;

import com.vpactually.entities.Order;

public interface OrderDomainService {

    void save(Order order);

    Order getOrderById(Integer id);

    void update(Order order);

    void deleteOrder(Integer id);
}

