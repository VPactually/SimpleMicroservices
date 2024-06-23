package com.vpactually.util;

import com.vpactually.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final AccountService service;

    @Autowired
    public DataInitializer(AccountService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.createAccount(1);
        service.add(1, 500);
        service.createAccount(2);
    }
}
