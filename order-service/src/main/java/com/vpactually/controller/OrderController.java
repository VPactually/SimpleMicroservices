package com.vpactually.controller;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.dto.OrderDTO;
import com.vpactually.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable String id) {
        return service.getOrderById(Integer.parseInt(id));
    }

    @PostMapping("/create")
    public void post(@RequestBody CreateOrderDTO createOrderDTO) {
        service.createOrder(createOrderDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteOrder(Integer.parseInt(id));
    }
}
