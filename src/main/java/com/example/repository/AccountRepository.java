package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findAccountByAccountId(Integer accountId);

    Account findAccountByUsername(String username);

    Account findAccountByUsernameAndPassword(String username, String password);
}
