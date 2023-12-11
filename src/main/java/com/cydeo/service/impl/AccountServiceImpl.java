package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    private final AccountMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {
        accountDTO.setCreationDate(new Date());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        // create Account object
        // save into the database
        // return the created object

       accountRepository.save(mapper.convertToEntity(accountDTO)); // save and return


    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);

    }

    @Override
    public void activate(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No account found"));
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO findAccountById(Long id) {
        return mapper.convertToDTO(
                accountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No account found"))
        );
    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        return accountRepository.findByAccountStatus(AccountStatus.ACTIVE)
                .stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {

      accountRepository.save(mapper.convertToEntity(accountDTO));
    }
}
