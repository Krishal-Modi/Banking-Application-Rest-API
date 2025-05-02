package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.mapper.UserMapper;
import com.example.BankingApplication.model.UserModel;
import com.example.BankingApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public UserModel login(UserModel userModel){
//        User addUser = userMapper.userModelToUser(userModel);
//        addUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
//        addUser = userRepository.save(addUser);
//        return userMapper.userToUserModel(addUser);
//    }


    // Update User Profile
    public UserModel updateUser(UserModel updateUserModel, String authenticatedEmail) {
        User existingUserByEmail = userRepository.findByEmail(authenticatedEmail);

        String userId = existingUserByEmail.getUserId();

        User updatedUser = userMapper.updateUserModel(updateUserModel, existingUserByEmail);

        updatedUser.setUserId(userId);
        updatedUser.setPassword(passwordEncoder.encode(updateUserModel.getPassword()));
        User savedUser = userRepository.save(updatedUser);

        return userMapper.userToUserModel(savedUser);
    }
}
