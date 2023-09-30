package com.cydeo;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BankSimulationAppApplication.class, args);

        // get account and transaction and service beans
//        AccountService accountService = context.getBean(AccountService.class);
//        TransactionService transactionService = context.getBean(TransactionService.class);
////
//        //create 2 accounts: sender and receiver
//        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
//        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 2L);
//        Account receiver2 = null;
////
//        System.out.println("============== accounts ==============");
//        accountService.listAllAccount().forEach(System.out::println);
//
//        transactionService.makeTransfer(sender,receiver,new BigDecimal(50),new Date(),"Transaction 1");
//
//        System.out.println("============== transactions ==============");
//        transactionService.findAllTransaction().forEach(System.out::println);
//
//        System.out.println("============== list accounts ==============");
//        accountService.listAllAccount().forEach(System.out::println);


    }

}
