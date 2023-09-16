package com.cydeo.service.impl;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class ransactionServiceImpl implements TransactionService {
    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        // if sender or receiver is null
        validateAccount(sender, receiver);
        // if sender and receiver have the same account
        // if sender has enough balance to make transfer
        // if both accounts are checking, if not, one of them saving, it needs to be same userID

        // make transfer
        return null;
    }

    private void validateAccount(Account sender, Account receiver) {
        //if any of account is null
        // if account ids are the same
        // if the account exist in the database (repository)
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}















