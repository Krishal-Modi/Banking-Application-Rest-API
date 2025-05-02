package com.example.BankingApplication.model;

import lombok.Data;

import java.util.List;

@Data
public class PassbookAccountModel {

    private Long accountNumber;
    private String accountType;
    private List<PassbookModel> passBookModel;

}
