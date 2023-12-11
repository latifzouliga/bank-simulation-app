package com.cydeo.service;

import com.cydeo.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    void createNewAccount(AccountDTO accountDTO);
    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    void activate(Long id);

    AccountDTO findAccountById(Long id);

    List<AccountDTO> listAllActiveAccounts();

    void updateAccount(AccountDTO accountDTO);
}
