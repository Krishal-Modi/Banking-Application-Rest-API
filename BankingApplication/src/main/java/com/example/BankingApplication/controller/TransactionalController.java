package com.example.BankingApplication.controller;


import com.example.BankingApplication.model.TransactionalModel;
import com.example.BankingApplication.service.TransactionalService;
import com.example.BankingApplication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionalController {

    @Autowired
    private TransactionalService transactionalService;

    @Autowired
    private JwtUtil jwtUtil;

    // Deposit
    @PutMapping("/deposit")
    public ResponseEntity<TransactionalModel> addBalance(@RequestBody TransactionalModel transactionalModel,
                                                         @RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader;
        String authenticatedEmail = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(transactionalService.deposit(transactionalModel));
    }


    // Withdrawal
    @PutMapping("/withdrawal")
    public ResponseEntity<TransactionalModel> deleteBalance(@RequestBody TransactionalModel transactionalModel,
                                                            @RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader;

        String authenticatedEmail = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(transactionalService.withdrawal(transactionalModel));
    }


    // Get Balance of a User Account
    @GetMapping("/currentBalance/{accountNumber}")
    public TransactionalModel getBankBalance(@PathVariable Long accountNumber,
                                             @RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader;
        String authenticatedEmail = jwtUtil.extractUsername(token);
        return transactionalService.listBankBalance(accountNumber);
    }

}
