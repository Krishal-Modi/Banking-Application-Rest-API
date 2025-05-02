package com.example.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "passbook")
@Data
public class Passbook {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "passbook_Id", updatable = false, nullable = false)
    private String passbookId;

    @Column(name = "credit")
    private Long credit;

    @Column(name = "debit")
    private Long debit;

    @Column(name = "total")
    private Long total;

    @Column(name = "date_time", updatable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @PrePersist
    public void prePersist() {
        this.dateTime = LocalDateTime.now();
    }

}
