package com.cydeo.service.impl;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.dto.AccountDTO;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository, TransactionMapper mapper) {
        this.accountService = accountService;

        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {

        if (!underConstruction) {

            // if sender or receiver is null
            validateAccount(sender, receiver);
            // if sender and receiver have the same account
            checkAccountOwnership(sender, receiver);
            // if sender has enough balance to make transfer
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            // if both accounts are checking, if not, one of them saving, it needs to be same userID

            // make transfer
            // after all validation are completed, and money is
            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
            // save and return transaction
            transactionRepository.save(mapper.convertToEntity(transactionDTO));
            return transactionDTO;
        }else {
            throw new UnderConstructionException("App is under construction, please come later");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if (checkSenderBalance(sender, amount)) {
            // update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
            // find sender by id
            AccountDTO senderAcc = accountService.retrieveById(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            accountService.updateAccount(senderAcc);

            AccountDTO receiverAcc = accountService.retrieveById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAcc);
        } else {
            throw new BalanceNotSufficientException("Balance is not enough fo this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }


    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        // write an if statement that checks if one of the accounts is saving
        // and user of sender or receiver is not the same, throw AccountOwnershipException

        if (((sender.getAccountType().equals(AccountType.SAVING) || (receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())))) {
            throw new AccountOwnershipException("Sender Account needs to be different than receiver account");

        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {

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

    private void findAccountById(Long id) {
        accountService.retrieveById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        return transactionRepository.findLast10Transactions()
                .stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        //get the list of transactions if account id is involved as a sender or receiver
        List<Transaction> transactionList = transactionRepository.findTransactionListByAccountId(id);
        //convert list of entity to dto and return it.
        return transactionList.stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }
}















