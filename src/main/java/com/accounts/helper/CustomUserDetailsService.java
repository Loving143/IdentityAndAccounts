package com.accounts.helper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.accounts.entity.Account;
import com.accounts.repository.AccountRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	private AccountRepository accountRepository;
	
	public CustomUserDetailsService (AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName){
		Account account =accountRepository.findAccountByEmail(userName).
				orElseThrow(()->new UsernameNotFoundException("Username not found!"));
		CustomUserDetails userDetails = new CustomUserDetails(account);
		return userDetails;
	}

}
