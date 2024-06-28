package com.vpactually.services.impl;

import com.vpactually.entities.Order;
import com.vpactually.handlers.RestaurantCreateCommandHandler;
import com.vpactually.services.RestaurantApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantApplicationServiceImpl implements RestaurantApplicationService {

    private final RestaurantCreateCommandHandler handler;
    private final RestaurantDomainServiceImpl service;

    public void checkOrder(Order order) {
        service.checkOrder(order);
        handler.handle(order);
    }

    public void rollback(Order order) {
        service.rollback(order);
    }

}
