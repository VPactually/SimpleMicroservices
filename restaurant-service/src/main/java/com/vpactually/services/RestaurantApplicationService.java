package com.vpactually.services;

import com.vpactually.entities.Order;

public interface RestaurantApplicationService {

    void checkOrder(Order order);

    void rollback(Order order);

}
