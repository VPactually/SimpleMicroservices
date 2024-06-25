package com.vpactually.controllers;

import com.vpactually.entities.Account;
import com.vpactually.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Account getAccount(@PathVariable String userId) {
        return service.getAccount(Integer.valueOf(userId));
    }

    @PostMapping
    public boolean createAccount(@RequestParam String userId) {
        return service.createAccount(Integer.parseInt(userId));
    }
}
