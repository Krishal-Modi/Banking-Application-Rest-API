package com.example.BankingApplication.controller;

import com.example.BankingApplication.model.UserAccountModel;
import com.example.BankingApplication.model.UserModel;
import com.example.BankingApplication.service.AdminService;
import com.example.BankingApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    // Adding New User
    @PostMapping("/signUp")
    public ResponseEntity<UserModel> addingUser(@RequestBody UserModel userModel){
        return ResponseEntity.ok(adminService.addUser(userModel));
    }

    // Fetch All the Users
    @GetMapping("/search")
    public List<UserAccountModel> searchUsers(@RequestParam(required = false) String search) {
        return adminService.searchAllUsers(search);
    }
}
