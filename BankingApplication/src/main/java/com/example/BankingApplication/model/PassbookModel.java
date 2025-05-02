package com.example.BankingApplication.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassbookModel {

    private LocalDateTime dateTime;
    private Long credit;
    private Long debit;
    private Long total;


}
