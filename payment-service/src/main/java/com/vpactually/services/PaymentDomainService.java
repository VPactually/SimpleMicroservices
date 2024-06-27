package com.vpactually.services;

import com.vpactually.entities.Account;
import com.vpactually.entities.Order;
import com.vpactually.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.vpactually.enums.OrderStatus.PAYMENT_FAILED;
import static com.vpactually.enums.OrderStatus.PAYMENT_SUCCESS;

@Service
@RequiredArgsConstructor
public class PaymentDomainService {

    private final AccountRepository repository;

    public Order process(Order order) {
        var account = repository.findByUserId(order.getUserId())
                .orElseThrow();
        if (account.getBalance() < order.getPrice()) {
            order.setStatus(PAYMENT_FAILED);
            return order;
        }
        account.setBalance(account.getBalance() - order.getPrice());
        repository.save(account);
        order.setStatus(PAYMENT_SUCCESS);
        return order;
    }

    public Account add(Integer userId, Integer amount) {
        var account = repository.findByUserId(userId)
                .orElseThrow();
        account.setBalance(account.getBalance() + amount);

        return repository.save(account);
    }

    public Account createAccount(Integer userId) {
        return repository.save(new Account(null, userId, 0));
    }

    public Account getAccount(Integer userId) {
        return repository.findByUserId(userId)
                .orElseThrow();
    }

}
