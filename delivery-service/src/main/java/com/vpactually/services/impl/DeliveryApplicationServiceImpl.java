package com.vpactually.services.impl;

import com.vpactually.entities.Order;
import com.vpactually.handlers.DeliveryCreateCommandHandler;
import com.vpactually.services.DeliveryApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryApplicationServiceImpl implements DeliveryApplicationService {

    private final DeliveryDomainServiceImpl service;
    private final DeliveryCreateCommandHandler handler;

    public void process(Order order) {
        service.process(order);
        handler.handle(order);
    }

    public void rollback(Order order) {
        service.rollback(order);
    }
}
