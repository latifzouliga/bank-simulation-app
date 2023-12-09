package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    public static List<AccountDTO> ACCOUNTLIST = new ArrayList<>();
//
//    public AccountDTO save(AccountDTO accountDTO){
//        ACCOUNTLIST.add(accountDTO);
//        return accountDTO;
//    }
//
//    public List<AccountDTO> findAll() {
//        return ACCOUNTLIST;
//    }
//
//    public AccountDTO findById(Long id) {
//       return findAll().stream()
//                .filter(account -> account.getId().equals(id))
//                .findAny()
//                .orElseThrow(() ->  new RecordNotFoundException( "Account not found" ));
//
//    }

}









