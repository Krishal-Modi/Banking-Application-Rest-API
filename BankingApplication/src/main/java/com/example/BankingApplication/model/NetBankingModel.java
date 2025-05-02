package com.example.BankingApplication.model;

import lombok.Data;

@Data
public class NetBankingModel {

    private Long amount;
    private SenderModel senderModel;
    private ReceiverModel receiverModel;

}
