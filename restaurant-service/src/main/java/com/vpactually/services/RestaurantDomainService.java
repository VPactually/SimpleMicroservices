package com.vpactually.services;

import com.vpactually.entities.Order;
import com.vpactually.entities.Product;

public interface RestaurantDomainService {

    void create(Product product);

    void checkOrder(Order order);

    void rollback(Order backupOrder);
}
