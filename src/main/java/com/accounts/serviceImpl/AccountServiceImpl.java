package com.accounts.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.accounts.dto.AccountDto;
import com.accounts.entity.Account;
import com.accounts.repository.AccountRepository;
import com.accounts.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	public AccountServiceImpl(AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepo;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public void signup(AccountDto account) {
		passwordEncoder.encode(account.getPassword());
		Account accountt = new Account(account);
		accountt.setPasswordHash(passwordEncoder.encode(account.getPassword()));
		accountRepository.save(accountt);
	}

}
