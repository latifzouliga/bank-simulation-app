package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    final private AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        // if sender or receiver is null
        validateAccount(sender, receiver);
        // if sender and receiver have the same account
        checkAccountOwnership(sender, receiver);
        // if sender has enough balance to make transfer
        executeBalanceAndUpdateIfRequired(amount, sender, receiver);
        // if both accounts are checking, if not, one of them saving, it needs to be same userID

        // make transfer
        // after all validation are completed, and money is
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .sender(sender.getId())
                .receiver(receiver.getId())
                .creationDate(creationDate)
                .message(message)
                .build();
        // save and return transaction
        return transactionRepository.save(transaction);

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if (checkSenderBalance(sender, amount)) {
            // update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        } else {
            throw new BalanceNotSufficientException("Balance is not enough fo this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }


    private void checkAccountOwnership(Account sender, Account receiver) {
        // write an if statement that checks if one of the accounts is saving
        // and user of sender or receiver is not the same, throw AccountOwnershipException

        if (((sender.getAccountType().equals(AccountType.SAVING) || (receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUseId().equals(receiver.getUseId())))) {
            throw new AccountOwnershipException("Sender Account needs to be different than receiver account");

        }
    }

    private void validateAccount(Account sender, Account receiver) {

        //if any of account is null
        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be not null");
        }

        // if account ids are the same
        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender Account needs to be different than receiver account");
        }

        // if the account exist in the database (repository)
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}















