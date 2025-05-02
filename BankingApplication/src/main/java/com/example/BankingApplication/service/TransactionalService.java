package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Passbook;
import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.exceptions.DataValidationException;
import com.example.BankingApplication.model.TransactionalModel;
import com.example.BankingApplication.repository.AccountRepository;
import com.example.BankingApplication.repository.PassbookRepository;
import com.example.BankingApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassbookRepository passbookRepository;

    // Deposit
    public TransactionalModel deposit(TransactionalModel transactionalModel) {

        Account account = accountRepository.findByAccountNumber(transactionalModel.getAccountNumber());
        User user = userRepository.findByUserId(account.getUser().getUserId());

        Long creditAmount = transactionalModel.getAmount();
//        Long debitAmount = null;

        account.setAccountBalance(account.getAccountBalance() + transactionalModel.getAmount());
        accountRepository.save(account);
        Long totalBalance = account.getAccountBalance();

        passBookEntry(account, user, null, creditAmount, totalBalance);

        transactionalModel.setAmount(account.getAccountBalance());
        return transactionalModel;
    }

    // Withdrawal
    public TransactionalModel withdrawal(TransactionalModel transactionalModel) {
        Account account = accountRepository.findByAccountNumber(transactionalModel.getAccountNumber());

        User user = userRepository.findByUserId(account.getUser().getUserId());
        Long debit = transactionalModel.getAmount();

        if(account.getAccountBalance() > transactionalModel.getAmount()){
            account.setAccountBalance(account.getAccountBalance() - transactionalModel.getAmount());
            accountRepository.save(account);

            Long totalBalance = account.getAccountBalance();
            passBookEntry(account, user, debit, null, totalBalance);

            transactionalModel.setAmount(account.getAccountBalance());
        }
        else if(account.getAccountBalance() == transactionalModel.getAmount()){
            System.out.println("Minimum Balance Should be 1000");
            throw new DataValidationException("Minimum Balance Should be 1000");
        }
        else{
            System.out.println("Not Sufficient Balance");
            throw new DataValidationException("No Sufficient Balance");
        }
        return transactionalModel;
    }


    // Passbook entry common function
    public void passBookEntry(Account account, User user, Long debitAmount, Long creditAmount, Long totalBalance) {

        Passbook passBook = new Passbook();
        passBook.setAccount(account);
        passBook.setUser(user);
        passBook.setDebit(debitAmount);
        passBook.setCredit(creditAmount);
        passBook.setTotal(totalBalance);
        passbookRepository.save(passBook);
    }


    // List current balance of user account
    public TransactionalModel listBankBalance(Long accountNumber){
        Account byAccountNumber = accountRepository.findByAccountNumber(accountNumber);

        if(accountNumber.equals(null)){
            throw new RuntimeException("User Account Not Exist");
        }

        TransactionalModel transModel = new TransactionalModel();
        transModel.setAmount(byAccountNumber.getAccountBalance());
        transModel.setAccountNumber(byAccountNumber.getAccountNumber());
        transModel.setAccountType(byAccountNumber.getAccountType());

        return transModel;
    }

}