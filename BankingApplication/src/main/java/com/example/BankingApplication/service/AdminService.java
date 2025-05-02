package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.mapper.AccountMapper;
import com.example.BankingApplication.mapper.UserMapper;
import com.example.BankingApplication.model.AccountModel;
import com.example.BankingApplication.model.UserAccountModel;
import com.example.BankingApplication.model.UserModel;
import com.example.BankingApplication.repository.AccountRepository;
import com.example.BankingApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Adding User with Encrypted Password
    public UserModel addUser(UserModel userModel){
        User addUser = userMapper.userModelToUser(userModel);
        addUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
        addUser = userRepository.save(addUser);
        return userMapper.userToUserModel(addUser);
    }


    // Get all users with their account
    public List<UserAccountModel> searchAllUsers(String search) {

        List<User> userList = userRepository.searchUsers(search);
        List<UserAccountModel> returnUserAccountModelList = userMapper.userListToUserModelList(userList);

        List<UserAccountModel> userAccountModelStream = returnUserAccountModelList.stream().map(userAccountModel -> {
            String userId = userAccountModel.getUserId();
            List<Account> byUserUserIds = accountRepository.findByUserUserIdIn(Collections.singletonList(userId));
            List<AccountModel> accountModelList = accountMapper.accountListToAccountModelList(byUserUserIds);
            userAccountModel.setAccount(accountModelList);
            return userAccountModel;
        }).toList();

        return userAccountModelStream;
    }

}
