package com.vpactually.handlers;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {

    public Order handle(CreateOrderDTO createOrderDTO) {
        return new Order(createOrderDTO);
    }

}
