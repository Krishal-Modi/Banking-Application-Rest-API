package com.example.BankingApplication.model;

import lombok.Data;

@Data
public class TransactionalModel {

    private Long accountNumber;
    private String accountType;
    private Long amount;

}
