package com.vpactually.services;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.dto.OrderDTO;
import com.vpactually.entities.Order;

public interface OrderApplicationService {

    OrderDTO create(CreateOrderDTO createOrderDTO);

    OrderDTO find(Integer id);

    void update(Order order);

    void destroy(Integer id);
}
