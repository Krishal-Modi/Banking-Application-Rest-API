package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Passbook;
import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.exceptions.DataNotFoundException;
import com.example.BankingApplication.exceptions.DataValidationException;
import com.example.BankingApplication.mapper.AccountMapper;
import com.example.BankingApplication.mapper.PassbookMapper;
import com.example.BankingApplication.mapper.UserMapper;
import com.example.BankingApplication.model.*;
import com.example.BankingApplication.repository.AccountRepository;
import com.example.BankingApplication.repository.PassbookRepository;
import com.example.BankingApplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PassbookRepository passbookRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PassbookMapper passbookMapper;

    @Autowired
    private TransactionalService transactionalService;


    // Adding account associated to user
    @Transactional
    public UserAccountModel addUserAccount(UserAccountModel userAccountModel, String authenticatedEmail) {
        // Find user by email
        User user = userRepository.findByEmail(authenticatedEmail);
        if (user == null) {
            throw new DataNotFoundException("User Not Found with email: " + authenticatedEmail);
        }

        // Convert AccountModel list to Account entities and set user
        List<Account> accounts = userAccountModel.getAccount().stream()
                .map(accountModel -> {
                    Account account = accountMapper.accountModelToAccount(accountModel);
                    account.setUser(user);
                    return account;
                }).toList();

        // Save accounts to database
        List<Account> savedAccounts = accountRepository.saveAll(accounts);

        // Convert saved accounts back to AccountModel list
        List<AccountModel> savedAccountModels = savedAccounts.stream()
                .map(account -> accountMapper.accountToAccountModel(account))
                .toList();

        // Convert User entity to UserAccountModel and set updated accounts
        UserAccountModel returnUserAccountModel = userMapper.userToUserAccountModel(user);
        returnUserAccountModel.setAccount(savedAccountModels);
        return returnUserAccountModel;
    }


    // Passbook of the authenticated user
    public UserPassbookModel getPassbookDetail(String email) {
        User user = userRepository.findByEmail(email);

        List<Account> accountList = accountRepository.findByUserUserId(user.getUserId());

        List<PassbookAccountModel> accountModelList = accountList.stream().map(account -> {
            PassbookAccountModel accountModel = accountMapper.accountToPassbookAccountModel(account);
            List<Passbook> passbookDetail = passbookRepository.findByAccountAccountId(account.getAccountId());
            accountModel.setPassBookModel(passbookMapper.passbookListToPassbookModelList(passbookDetail));
            return accountModel;
        }).toList();

        UserPassbookModel userPassBookModel = passbookMapper.userToUserPassBookModel(user);
        userPassBookModel.setAccountModel(accountModelList);

        return userPassBookModel;
    }


    // Netbanking (Transferring money from one user to another user)
    public NetBankingModel moneyTransfer(Long senderAccountNo, Long receiverAccountNo, Long amount) {

        // Sender
        Account senderAccount = accountRepository.findByAccountNumber(senderAccountNo);
        User senderUser = userRepository.findByUserId(senderAccount.getUser().getUserId());

        if(senderAccount.getAccountBalance() <= amount){
            throw new DataValidationException("Insufficient Balance");
        }

        senderAccount.setAccountBalance(senderAccount.getAccountBalance() - amount);
        accountRepository.save(senderAccount);
        Long senderBalance = senderAccount.getAccountBalance();

        SenderModel senderModel = new SenderModel();
        senderModel.setAccountNumber(senderAccount.getAccountNumber());
        senderModel.setFirstName(senderUser.getFirstName());
        senderModel.setLastName(senderUser.getLastName());

        transactionalService.passBookEntry(senderAccount, senderUser, amount, null, senderBalance);

        // Receiver
        Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNo);
        User receiverUser = userRepository.findByUserId(receiverAccount.getUser().getUserId());

        receiverAccount.setAccountBalance(receiverAccount.getAccountBalance() + amount);
        accountRepository.save(receiverAccount);
        Long receiverBalance = receiverAccount.getAccountBalance();

        ReceiverModel receiverModel = new ReceiverModel();
        receiverModel.setAccountNumber(receiverAccount.getAccountNumber());
        receiverModel.setFirstName(receiverUser.getFirstName());
        receiverModel.setLastName(receiverUser.getLastName());

        transactionalService.passBookEntry(receiverAccount, receiverUser, null, amount, receiverBalance);

        NetBankingModel netBanking = new NetBankingModel();
        netBanking.setAmount(amount);
        netBanking.setSenderModel(senderModel);
        netBanking.setReceiverModel(receiverModel);

        return netBanking;
    }


    // Loan calculator for calculating loan and installment
    public LoanModel loanCalculate(double principal, double rateOfInterest, double year) {

        double simpleInterest = (principal * rateOfInterest * year) / 100;
        double totalAmount = principal + simpleInterest;
        double emi = totalAmount / (year * 12);

        LoanModel loanCalculate = new LoanModel();
        loanCalculate.setPrincipal(principal);
        loanCalculate.setRateOfInterest(rateOfInterest);
        loanCalculate.setYear(year);
        loanCalculate.setSimpleInterest(simpleInterest);
        loanCalculate.setTotalAmount(totalAmount);
        loanCalculate.setEmi(emi);

        return loanCalculate;
    }
}