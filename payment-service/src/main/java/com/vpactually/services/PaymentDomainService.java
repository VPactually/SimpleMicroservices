package com.vpactually.services;

import com.vpactually.entities.Account;
import com.vpactually.entities.Order;

public interface PaymentDomainService {

    void process(Order order);

    Account add(Integer userId, Integer amount);

    Account createAccount(Integer userId);

    Account getAccount(Integer userId);

}
