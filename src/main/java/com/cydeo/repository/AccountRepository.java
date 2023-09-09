package com.cydeo.repository;

import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountRepository {

    public static List<Account> ACCOUNTLIST = new ArrayList<>();

    public Account save(Account account){
        ACCOUNTLIST.add(account);
        return account;
    }

}
