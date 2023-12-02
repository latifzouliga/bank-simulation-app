package com.cydeo.controller;


import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;


@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    private String showAccounts(Model model) {
        model.addAttribute("accountList", accountService.listAllAccount());
        return "/account/index";
    }

    @GetMapping("/create-form")
    private String createAccount(Model model) {
        model.addAttribute("account", new AccountDTO());
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }

    @PostMapping("/create")
    private String insertAccount(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("accountTypes", AccountType.values());
            return "/account/create-account";
        }
        accountService.createNewAccount(accountDTO.getBalance(),
                                        new Date(),
                                        accountDTO.getAccountType(),
                                        accountDTO.getUseId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{uuid}")
    private String deleteAccount(@PathVariable Long uuid){
        System.out.println(uuid);
        accountService.deleteAccount(uuid);
        return "redirect:/index";
    }

    @GetMapping("/activate/{uuid}")
    private String activateAccount(@PathVariable Long uuid){
        accountService.activate(uuid);
        return "redirect:/index";
    }


}
