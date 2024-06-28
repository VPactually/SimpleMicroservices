package com.vpactually.services;

import com.vpactually.dto.AccountDTO;
import com.vpactually.entities.Order;

public interface PaymentApplicationService {

    AccountDTO createAccount(Integer userId);

    AccountDTO getAccount(Integer userId);

    AccountDTO addMoney(Integer userId, Integer amount);

    void payOrder(Order order);

    void rollback(Order order);

}
