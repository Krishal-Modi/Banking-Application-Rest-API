package com.example.BankingApplication.exceptions;

import lombok.Data;

@Data
public class DataNotFoundException extends RuntimeException {
  public DataNotFoundException(String message) {
    super(message);
  }
}
