package com.example.BankingApplication.model;

import lombok.Data;

import java.util.List;

@Data
public class UserAccountModel {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private List<AccountModel> account;

}
