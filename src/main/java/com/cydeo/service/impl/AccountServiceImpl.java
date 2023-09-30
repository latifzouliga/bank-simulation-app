package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        // create Account object
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .useId(userId)
                .balance(balance)
                .accountType(accountType)
                .creationDate(creationDate)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        // save into the database
        // return the created object
        return accountRepository.save(account); // save and return

    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }
}
