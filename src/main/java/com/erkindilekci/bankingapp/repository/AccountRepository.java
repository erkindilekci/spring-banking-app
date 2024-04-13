package com.erkindilekci.bankingapp.repository;

import com.erkindilekci.bankingapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
