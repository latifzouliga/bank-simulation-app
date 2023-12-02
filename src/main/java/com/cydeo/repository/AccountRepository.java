package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {

    public static List<AccountDTO> ACCOUNTLIST = new ArrayList<>();

    public AccountDTO save(AccountDTO accountDTO){
        ACCOUNTLIST.add(accountDTO);
        return accountDTO;
    }

    public List<AccountDTO> findAll() {
        return ACCOUNTLIST;
    }

    public AccountDTO findById(UUID id) {
       return findAll().stream()
                .filter(account -> account.getId().equals(id))
                .findAny()
                .orElseThrow(() ->  new RecordNotFoundException( "Account not found" ));

    }
}









