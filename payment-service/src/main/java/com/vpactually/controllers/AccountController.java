package com.vpactually.controllers;

import com.vpactually.dto.AccountDTO;
import com.vpactually.entities.Order;
import com.vpactually.services.PaymentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final PaymentApplicationService service;

    @PostMapping
    public AccountDTO createAccount(@RequestParam String userId) {
        return service.createAccount(Integer.parseInt(userId));
    }

    @GetMapping("/{userId}")
    public AccountDTO getAccount(@PathVariable String userId) {
        return service.getAccount(Integer.valueOf(userId));
    }

    @PostMapping("/add")
    public AccountDTO add(@RequestBody Order order) {
        return service.addMoney(order.getUserId(), order.getPrice());
    }
}
