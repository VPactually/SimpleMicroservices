package com.vpactually.mappers;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.dto.OrderDTO;
import com.vpactually.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO map(Order order) {
        return new OrderDTO(order);
    }

    public Order map(CreateOrderDTO createOrderDTO) {
        return new Order(createOrderDTO);
    }
}
