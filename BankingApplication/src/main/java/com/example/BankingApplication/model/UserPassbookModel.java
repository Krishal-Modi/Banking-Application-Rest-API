package com.example.BankingApplication.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserPassbookModel {

    private String firstName;
    private String email;
    private List<PassbookAccountModel> accountModel;

}
