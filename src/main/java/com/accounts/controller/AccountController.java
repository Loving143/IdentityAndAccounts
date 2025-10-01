package com.accounts.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accounts.client.EmailClient;
import com.accounts.client.OtpClient;
import com.accounts.config.response.LoginResponse;
import com.accounts.dto.AccountDto;
import com.accounts.dto.OtpDto;
import com.accounts.helper.CustomUserDetails;
import com.accounts.helper.JwtUtil;
import com.accounts.request.EmailRequest;
import com.accounts.service.AccountService;

@RestController
@RequestMapping("/auth")
public class AccountController {
		
		private final AccountService accountService;
		private final OtpClient otpClient;
		private final EmailClient emailClient;
		private final AuthenticationManager authenticationManager;
		private final JwtUtil jwtUtil;
	    public AccountController(AuthenticationManager authenticationManager,AccountService accountService
	    		,JwtUtil jwtUtil,OtpClient otpClient,EmailClient emailClient) {
	        this.accountService = accountService;
			this.authenticationManager = authenticationManager;
			this.jwtUtil = jwtUtil;
			this.otpClient = otpClient;
			this.emailClient = emailClient;
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody AccountDto accountDto) {
	        try {
	            Authentication authenticationRequest =
	                    new UsernamePasswordAuthenticationToken(accountDto.getUserName(), accountDto.getPassword());

	            Authentication authenticationResponse =
	                    authenticationManager.authenticate(authenticationRequest);

	            CustomUserDetails userDetails =
	                    (CustomUserDetails) authenticationResponse.getPrincipal();
	            OtpDto otp = otpClient.generateOtp(accountDto.getUserName());
	            // ✅ Send codeHash to Email service
	            
	            Map<String, Object> model = new HashMap<>();
	            model.put("name",otp.getUserName());
	            model.put("email", otp.getUserName());
	            model.put("subscription", "Premium");
	            model.put("otp",otp.getCodeHash());
	            model.put("bankName","Medicare");
	            EmailRequest req = new EmailRequest(otp.getUserName(),"Your otp for login to Medicare account is : "
	  	      		  +otp.getCodeHash() +".Please do not share it with any one.","OTP Verification",model);
	            emailClient.sendOtp(req);
	        
	            String jwtToken = jwtUtil.generateToken(accountDto.getUserName(),userDetails.getAuthorities());
	            LoginResponse response = new LoginResponse(jwtToken,accountDto.getUserName());
	            return ResponseEntity.ok().body(response);

	        }
	        catch (AuthenticationException ex) {
	        	throw new UsernameNotFoundException("Invalid username and password!!");
	        }
	    }
	    
	    @PostMapping("/signup")
	    public ResponseEntity<?>signUp(@RequestBody AccountDto account){
	    	accountService.signup(account);
	    	return ResponseEntity.ok("User registered successfully!");
	    }
	    
	

}
