package com.accounts.service;

import org.springframework.http.ResponseEntity;

import com.accounts.dto.AccountDto;
import com.accounts.dto.ChangePasswordRequest;
import com.accounts.response.PasswordChangeResult;

public interface AccountService {

	void signup(AccountDto account);
	PasswordChangeResult changePassword(ChangePasswordRequest req);

}
