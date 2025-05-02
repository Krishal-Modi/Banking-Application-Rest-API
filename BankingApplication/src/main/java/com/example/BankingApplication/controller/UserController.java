package com.example.BankingApplication.controller;


import com.example.BankingApplication.model.UserModel;
import com.example.BankingApplication.repository.UserRepository;
import com.example.BankingApplication.service.CustomUserDetailsService;
import com.example.BankingApplication.service.UserService;
import com.example.BankingApplication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel userModel) {
        try {
            // Wrap email and password into UsernamePasswordAuthenticationToken
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(userModel.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Email or Password");
        }
    }

    // Updating New User
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateUser(@RequestBody UserModel updatedUserModel,
                                        @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(0);
        String authenticatedEmail = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(userService.updateUser(updatedUserModel, authenticatedEmail));
    }

}
