package com.vpactually.util;

import com.vpactually.entities.Product;
import com.vpactually.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RestaurantService service;

    @Autowired
    public DataInitializer(RestaurantService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.create(new Product(null, "pizza", 5));
        service.create(new Product(null, "sushi", 10));
        service.create(new Product(null, "salad", 5));
    }
}
