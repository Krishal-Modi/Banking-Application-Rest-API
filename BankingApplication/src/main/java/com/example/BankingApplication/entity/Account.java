package com.example.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id", nullable = false, updatable = false)
    private String accountId;

    @Column(name = "account_number", unique = true, nullable = false)
    private Long accountNumber;

    @Column(name = "account_branch", nullable = false)
    private String accountBranch;

    @Column(name = "account_balance", nullable = false)
    private Long accountBalance;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Passbook> passbook;

    @PrePersist
    protected void onCreate() {
        this.accountId = UUID.randomUUID().toString();
        this.accountNumber = generateAccountNumber();
    }

    private Long generateAccountNumber() {
        Random random = new Random();
        long min = 800000000000L;
        long max = 899999999999L;
        return min + ((long) (random.nextDouble() * (max - min)));
    }
}
