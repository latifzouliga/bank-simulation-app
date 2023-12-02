package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping
public class TransactionController {

    private final AccountService accountService;
    public final TransactionService transactionService;


    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    private String getMakeTransfer(Model model) {
        model.addAttribute("transaction", TransactionDTO.builder().build());
        model.addAttribute("accounts", accountService.listAllAccount());
        model.addAttribute("lastTransactions", transactionService.last10Transactions());
        return "/transaction/make-transfer";
    }


    @PostMapping("/make-transfer")
    private String getMakeTransfer(@Valid @ModelAttribute("transaction") TransactionDTO transactionDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccount());
            model.addAttribute("lastTransactions", transactionService.last10Transactions());
            return "/transaction/make-transfer";
        }
        AccountDTO sender = accountService.findAccountById(transactionDTO.getSender());
        AccountDTO receiver = accountService.findAccountById(transactionDTO.getReceiver());

        transactionService.makeTransfer(sender, receiver, transactionDTO.getAmount(),new Date(), transactionDTO.getMessage());
        System.out.println(transactionDTO);
        return "redirect:/make-transfer";
    }

    @GetMapping("/transactions/{uuid}")
    private String getTransactions(@PathVariable UUID uuid, Model model){
        model.addAttribute("transactions",transactionService.findTransactionById(uuid));
        return "/transaction/transactions";
    }


}
