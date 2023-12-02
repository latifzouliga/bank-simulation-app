package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
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
        model.addAttribute("transaction", Transaction.builder().build());
        model.addAttribute("accounts", accountService.listAllAccount());
        model.addAttribute("lastTransactions", transactionService.last10Transactions());
        return "/transaction/make-transfer";
    }


    @PostMapping("/make-transfer")
    private String getMakeTransfer(@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccount());
            model.addAttribute("lastTransactions", transactionService.last10Transactions());
            return "/transaction/make-transfer";
        }
        Account sender = accountService.findAccountById(transaction.getSender());
        Account receiver = accountService.findAccountById(transaction.getReceiver());

        transactionService.makeTransfer(sender, receiver, transaction.getAmount(),new Date(),transaction.getMessage());
        System.out.println(transaction);
        return "redirect:/make-transfer";
    }

    @GetMapping("/transactions/{uuid}")
    private String getTransactions(@PathVariable UUID uuid, Model model){
        model.addAttribute("transactions",transactionService.findTransactionById(uuid));
        return "/transaction/transactions";
    }


}
