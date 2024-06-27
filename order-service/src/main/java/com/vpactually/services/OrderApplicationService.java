package com.vpactually.services;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.dto.OrderDTO;
import com.vpactually.entities.Order;
import com.vpactually.handlers.OrderCreateCommandHandler;
import com.vpactually.mappers.OrderMapper;
import com.vpactually.messaging.saga.OrderSaga;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderCreateCommandHandler handler;
    private final OrderDomainService service;
    private final OrderMapper mapper;
    private final OrderSaga saga;

    public OrderDTO create(CreateOrderDTO createOrderDTO) {
        var order = handler.handle(createOrderDTO);
        service.save(order);
        saga.doSaga(order);
        return mapper.map(order);
    }

    public OrderDTO find(Integer id) {
        return mapper.map(service.getOrderById(id));
    }

    public void update(Order order) {
        service.update(order);
        saga.doSaga(order);
    }

    public void destroy(Integer id) {
        service.deleteOrder(id);
    }
}
