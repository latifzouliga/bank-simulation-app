package com.cydeo.controller;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Controller
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/index")
    public String showAccounts(Model model) {
        model.addAttribute("accountList", accountService.listAllAccount());
        return "/account/index";
    }

    @GetMapping("/create-form")
    public String createAccount(Model model) {
        model.addAttribute("account", Account.builder().build());
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }

    @PostMapping("/create")
    public String insertAccount(@ModelAttribute("account") Account account) {
        accountService.createNewAccount(account.getBalance(), new Date(), account.getAccountType(), account.getUseId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{uuid}")
    public String deleteAccount(@PathVariable UUID uuid){
        System.out.println(uuid);
        accountService.deleteAccount(uuid);
        return "redirect:/index";
    }

    @GetMapping("/activate/{uuid}")
    public String activateAccount(@PathVariable UUID uuid){
        accountService.activate(uuid);
        return "redirect:/index";
    }


}
