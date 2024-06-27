package com.vpactually.services;

import com.vpactually.entities.Order;
import com.vpactually.handlers.RestaurantCreateCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantApplicationService {

    private final RestaurantCreateCommandHandler handler;
    private final RestaurantDomainService service;

    public void checkOrder(Order order) {
        service.checkOrder(order);
        handler.handle(order);
    }

}
