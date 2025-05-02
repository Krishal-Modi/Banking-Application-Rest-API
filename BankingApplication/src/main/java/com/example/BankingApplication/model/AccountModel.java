package com.example.BankingApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountModel {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long accountNumber;
    private String accountBranch;
    private Long accountBalance;
    private String accountType;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String userId;


}
