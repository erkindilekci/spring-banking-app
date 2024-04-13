package com.erkindilekci.bankingapp.service;

import com.erkindilekci.bankingapp.dto.AccountDto;
import com.erkindilekci.bankingapp.entity.Account;
import com.erkindilekci.bankingapp.mapper.AccountMapper;
import com.erkindilekci.bankingapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.erkindilekci.bankingapp.mapper.AccountMapper.mapToAccount;
import static com.erkindilekci.bankingapp.mapper.AccountMapper.mapToAccountDto;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No account found with id: " + id));
        return mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No account found with id: " + id));

        double totalAmount = account.getBalance() + amount;
        account.setBalance(totalAmount);

        Account savedAccount = accountRepository.save(account);
        return mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No account found with id: " + id));

        double accountBalance = account.getBalance();

        if (amount > accountBalance) {
            throw new RuntimeException("Insufficient balance");
        }

        double remainingAmount = accountBalance - amount;
        account.setBalance(remainingAmount);

        Account savedAccount = accountRepository.save(account);
        return mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
    }

    @Override
    public void deleteAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No account found with id: " + id));
        accountRepository.delete(account);
    }
}
