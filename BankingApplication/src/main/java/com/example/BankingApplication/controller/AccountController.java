package com.example.BankingApplication.controller;

import com.example.BankingApplication.model.LoanModel;
import com.example.BankingApplication.model.NetBankingModel;
import com.example.BankingApplication.model.UserAccountModel;
import com.example.BankingApplication.model.UserPassbookModel;
import com.example.BankingApplication.service.AccountService;
import com.example.BankingApplication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userAccount")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/addingAccount")
    public ResponseEntity<UserAccountModel> insertedUserAccount(
            @RequestBody UserAccountModel userAccountModel,
            @RequestHeader("Authorization") String tokenHeader) {

        // Remove "Bearer " prefix and extract token
        String token = tokenHeader.substring(0);

        String authenticatedEmail = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(accountService.addUserAccount(userAccountModel, authenticatedEmail));
    }


    @GetMapping("/passbook")
    public ResponseEntity<UserPassbookModel> passBookDetail(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(0);
        String authenticatedEmail = jwtUtil.extractUsername(token);
        return new ResponseEntity<>(accountService.getPassbookDetail(authenticatedEmail), HttpStatus.OK);
    }


    @PutMapping("/netBanking")
    public ResponseEntity<NetBankingModel> netBanking(
                        @RequestParam Long senderAccountNumber,
                        @RequestParam Long receiverAccountNumber,
                        @RequestParam Long amount,
                        @RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader.substring(0);
        String authenticatedEmail = jwtUtil.extractUsername(token);
        return new ResponseEntity<>(accountService.moneyTransfer(senderAccountNumber, receiverAccountNumber, amount), HttpStatus.OK);
    }


    @GetMapping("/loanCalculator")
    public ResponseEntity<LoanModel> calculateLoan(
                        @RequestParam double principal,
                        @RequestParam double rateOfInterest,
                        @RequestParam double year,
                        @RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader.substring(0);
        String authenticatedEmail = jwtUtil.extractUsername(token);
        return new ResponseEntity<>(accountService.loanCalculate(principal, rateOfInterest, year), HttpStatus.OK);
    }


}
