package com.vpactually.util;

import com.vpactually.services.PaymentDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final PaymentDomainService service;

    @Autowired
    public DataInitializer(PaymentDomainService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.createAccount(1);
        service.add(1, 500);
        service.createAccount(2);
    }
}
