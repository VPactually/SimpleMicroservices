package com.vpactually.mappers;

import com.vpactually.dto.AccountDTO;
import com.vpactually.entities.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    public AccountDTO map(Account account) {
        return new AccountDTO(account.getUserId(), account.getBalance());
    }

}
