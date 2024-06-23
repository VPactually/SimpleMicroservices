package com.vpactually.controllers;

import com.vpactually.entities.Order;
import com.vpactually.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final AccountService service;

    @Autowired
    public PaymentController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/pay")
    public boolean pay(@RequestBody Order order) {
        return service.process(order.getUserId(), order.getPrice());
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Order order) {
        return service.add(order.getUserId(), order.getPrice());
    }
}
