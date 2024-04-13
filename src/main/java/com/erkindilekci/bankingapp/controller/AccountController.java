package com.erkindilekci.bankingapp.controller;

import com.erkindilekci.bankingapp.dto.AccountDto;
import com.erkindilekci.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) {
        return new ResponseEntity<>(accountService.getAccountById(accountId), HttpStatus.OK);
    }

    @PutMapping("{accountId}/deposit")
    public ResponseEntity<AccountDto> deposit(
            @PathVariable("accountId") Long id,
            @RequestBody Map<String, Double> request
    ) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @PutMapping("{accountId}/withdraw")
    public ResponseEntity<AccountDto> withdraw(
            @PathVariable("accountId") Long id,
            @RequestBody Map<String, Double> request
    ) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<String> deleteAccountById(@PathVariable("accountId") Long id) {
        accountService.deleteAccountById(id);
        return new ResponseEntity<>("Account is deleted successfully", HttpStatus.OK);
    }
}
