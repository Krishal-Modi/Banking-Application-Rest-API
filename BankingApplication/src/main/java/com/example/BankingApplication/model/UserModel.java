package com.example.BankingApplication.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserModel {
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String password;
    private String area;
    private String gender;

}
