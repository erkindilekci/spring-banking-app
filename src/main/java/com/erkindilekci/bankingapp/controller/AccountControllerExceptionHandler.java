package com.erkindilekci.bankingapp.controller;

import com.erkindilekci.bankingapp.error.AccountErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleException(Exception e) {
        AccountErrorResponse error = new AccountErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getLocalizedMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
