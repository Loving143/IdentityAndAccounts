package com.accounts.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.accounts.dto.AccountDto;
import com.accounts.dto.ChangePasswordRequest;
import com.accounts.entity.Account;
import com.accounts.entity.LoginAttempt;
import com.accounts.enumm.AttemptType;
import com.accounts.helper.RateLimitResult;
import com.accounts.repository.AccountRepository;
import com.accounts.repository.LoginAttemptRepository;
import com.accounts.response.PasswordChangeResult;
import com.accounts.service.AccountService;
import com.accounts.service.TokenBucketRateLimitService;

@Service
public class AccountServiceImpl implements AccountService{
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenBucketRateLimitService rateLimitService;
	private final LoginAttemptRepository loginAttemptRepository;
	
	public AccountServiceImpl(AccountRepository accountRepo, PasswordEncoder passwordEncoder, TokenBucketRateLimitService rateLimitService,
			LoginAttemptRepository loginAttemptRepository) {
		this.accountRepository = accountRepo;
		this.passwordEncoder = passwordEncoder;
		this.rateLimitService = rateLimitService;
		this.loginAttemptRepository = loginAttemptRepository;
	}
	
	@Override
	public void signup(AccountDto account) {
		passwordEncoder.encode(account.getPassword());
		Account accountt = new Account(account);
		accountt.setPasswordHash(passwordEncoder.encode(account.getPassword()));
		accountRepository.save(accountt);
	}
	
	@Override
	public PasswordChangeResult changePassword(ChangePasswordRequest req) {
		
		RateLimitResult rateLimitResult = rateLimitService.checkRateLimit(req.getUserName(), AttemptType.PASSWORD_CHANGE);
		Optional<Account> accountOpt = accountRepository.findAccountByEmail(req.getUserName());
		 if (accountOpt.isEmpty()) {
	            logAttempt(req.getUserName(), AttemptType.PASSWORD_CHANGE, "10.226.41.203", "kamlesh", false, "ACCOUNT_NOT_FOUND");
	            return PasswordChangeResult.userNotFound();
	        }
	        
	        Account account = accountOpt.get();
        if (!rateLimitResult.isAllowed()) {
        	System.out.println("Not allowed");
            logAttempt(account, AttemptType.PASSWORD_CHANGE, "10.226.41.203", "kamlesh", false, "RATE_LIMITED");
            return PasswordChangeResult.rateLimited(rateLimitResult.getWaitTimeMs());
        }
         
        if (!passwordEncoder.matches(req.getOldPassword(), account.getPasswordHash())) {
        	System.out.println("Password not matched!!");
            logAttempt(account, AttemptType.PASSWORD_CHANGE, "10.226.41.203", "kamlesh", false, "INVALID_CURRENT_PASSWORD");
            return PasswordChangeResult.invalidCurrentPassword();
        }
        account.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        accountRepository.save(account);
        
        logAttempt(account, AttemptType.PASSWORD_CHANGE,"10.226.41.203", "kamlesh", true, "SUCCESS");
        return PasswordChangeResult.success();
	}
	
	 private void logAttempt(Account account, AttemptType attemptType, String ipAddress,
             String userAgent, boolean successful, String failureReason) {

	LoginAttempt attempt = new LoginAttempt();
	attempt.setAccount(account);  // Account relationship set karo
	attempt.setUserName(account.getEmail());
	attempt.setAttemptType(attemptType);
	attempt.setIpAddress(ipAddress);
	attempt.setUserAgent(userAgent);
	attempt.setSuccessful(successful);
	attempt.setFailureReason(failureReason);
	attempt.setAttemptTime(LocalDateTime.now());
	
	loginAttemptRepository.save(attempt);

}
	 private void logAttempt(String username, AttemptType attemptType, String ipAddress,
             String userAgent, boolean successful, String failureReason) {

		// Naya LoginAttempt object banayo
		 System.out.println(username);
		LoginAttempt attempt = new LoginAttempt();
		attempt.setUserName(username);
		attempt.setAttemptType(attemptType);
		attempt.setIpAddress(ipAddress);
		attempt.setUserAgent(userAgent);
		attempt.setSuccessful(successful);
		attempt.setFailureReason(failureReason);
		attempt.setAttemptTime(LocalDateTime.now());
		
		// Database mein save karo
		loginAttemptRepository.save(attempt);
		}
}
