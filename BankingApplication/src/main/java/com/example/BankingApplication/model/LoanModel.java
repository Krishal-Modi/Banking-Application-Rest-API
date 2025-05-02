package com.example.BankingApplication.model;

import lombok.Data;

@Data
public class LoanModel {

    private double principal;
    private double rateOfInterest;
    private double year;
    private double simpleInterest;
    private double totalAmount;
    private double emi;

}
