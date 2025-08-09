package com.example.service;

import java.util.*;

import javax.transaction.Transactional;

import com.example.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account persistAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccountByAccountId(Integer accountId){
        return accountRepository.findAccountByAccountId(accountId);
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findAccountByUsername(username);
    }

    public Account getAccountByUsernameAndPassword(String username, String password){
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }

    @Transactional
    public Account createNewAccount(String username, String password){
        return persistAccount(new Account(username, password));
    }
}
