package com.example.BankingApplication.exceptions.handler;

import com.example.BankingApplication.exceptions.DataNotFoundException;
import com.example.BankingApplication.exceptions.DataValidationException;
import com.example.BankingApplication.model.error.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.BankingApplication.model.error.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.BankingApplication")
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerDataNotFoundException(DataNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ErrorType.MISSING_DATA);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DataValidationException.class})
    public ResponseEntity<ErrorResponse> handlerDataValidationException(DataValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ErrorType.INVALID_DATA);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("An Unexpected Error Occurred", ErrorType.INTERNAL_ERROR);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
