package com.vpactually.services;

import com.vpactually.entities.Order;
import com.vpactually.handlers.DeliveryCreateCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryApplicationService {

    private final DeliveryDomainService service;
    private final DeliveryCreateCommandHandler handler;

    public void process(Order order) {
        service.process(order);
        handler.handle(order);
    }

    public void rollback(Order order) {
        service.rollback(order);
    }
}
