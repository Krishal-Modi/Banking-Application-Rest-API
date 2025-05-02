package com.example.BankingApplication.repository;

import com.example.BankingApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    boolean existsByUserUserId(String userId);

    List<Account> findByUserUserIdIn(List<String> userId);

    Account findByAccountNumber(Long accountNumber);

    List<Account> findByUserUserId(String userId);

}
