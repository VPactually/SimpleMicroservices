package com.vpactually.services;

import com.vpactually.entities.Order;


public interface DeliveryApplicationService {

    void process(Order order);

    void rollback(Order order);
}
