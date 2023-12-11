package com.cydeo.converter;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<String, AccountDTO> {


    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    public AccountConverter(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }


    @Override
    public AccountDTO convert(String source) {
        return mapper.convertToDTO(
                accountRepository.findByUserId(Long.valueOf(source)).orElseThrow()
        );
    }
}
