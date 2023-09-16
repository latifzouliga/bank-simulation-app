package com.cydeo.repository;

import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {

    public static List<Account> ACCOUNTLIST = new ArrayList<>();

    public Account save(Account account){
        ACCOUNTLIST.add(account);
        return account;
    }

    public List<Account> findAll() {
        return ACCOUNTLIST;
    }

    public Account findById(UUID id) {
       return findAll().stream()
                .filter(account -> account.getId().equals(id))
                .findAny()
                .orElseThrow(() ->  new RecordNotFoundException( "Account not found" ));

    }
}









