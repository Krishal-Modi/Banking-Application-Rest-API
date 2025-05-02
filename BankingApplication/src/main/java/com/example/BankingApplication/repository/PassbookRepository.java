package com.example.BankingApplication.repository;

import com.example.BankingApplication.entity.Passbook;
import com.example.BankingApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassbookRepository extends JpaRepository<Passbook, String> {

    List<Passbook> findByAccountAccountId(String accountId);

}
