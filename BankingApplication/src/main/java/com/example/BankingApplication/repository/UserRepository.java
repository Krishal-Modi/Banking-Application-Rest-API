package com.example.BankingApplication.repository;

import com.example.BankingApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT DISTINCT u.* FROM users u " +
            "LEFT JOIN accounts a ON u.user_id = a.user_id " +
            "WHERE " +
            "LOWER(u.first_name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.phone_number) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.area) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR CAST(a.account_number AS TEXT) LIKE CONCAT('%', :search, '%') " +
            "OR LOWER(a.account_branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.account_type) LIKE LOWER(CONCAT('%', :search, '%'))",
            nativeQuery = true)
    List<User> searchUsers(@Param("search") String search);

    User findByEmail(String email);

    User findByUserId(String userId);

}
