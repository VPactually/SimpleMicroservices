package com.vpactually.services.impl;

import com.vpactually.dto.AccountDTO;
import com.vpactually.entities.Order;
import com.vpactually.handlers.PaymentCreateCommandHandler;
import com.vpactually.mappers.PaymentMapper;
import com.vpactually.services.PaymentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentApplicationServiceImpl implements PaymentApplicationService {

    private final PaymentCreateCommandHandler handler;
    private final PaymentDomainServiceImpl service;
    private final PaymentMapper mapper;

    public AccountDTO createAccount(Integer userId) {
        return mapper.map(service.createAccount(userId));
    }

    public AccountDTO getAccount(Integer userId) {
        return mapper.map(service.getAccount(userId));
    }

    public AccountDTO addMoney(Integer userId, Integer amount) {
        return mapper.map(service.add(userId, amount));
    }

    public void payOrder(Order order) {
        service.process(order);
        handler.handle(order);
    }

    public void rollback(Order order) {
        service.add(order.getUserId(), order.getPrice());
    }

}
