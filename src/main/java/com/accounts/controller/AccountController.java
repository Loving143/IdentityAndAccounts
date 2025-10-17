package com.accounts.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accounts.client.EmailClient;
import com.accounts.client.OtpClient;
import com.accounts.config.response.LoginResponse;
import com.accounts.dto.AccountDto;
import com.accounts.dto.ChangePasswordRequest;
import com.accounts.dto.ErrorResponse;
import com.accounts.dto.OtpDto;
import com.accounts.helper.CustomUserDetails;
import com.accounts.helper.JwtUtil;
import com.accounts.request.EmailRequest;
import com.accounts.request.ValidateOtpRequest;
import com.accounts.response.AccountResponse;
import com.accounts.response.PasswordChangeResult;
import com.accounts.response.SuccessfulLoginResponse;
import com.accounts.service.AccountService;
import com.accounts.serviceImpl.EmailService;
import com.accounts.serviceImpl.OtpService;

@RestController
@RequestMapping("/auth")
public class AccountController {
		
		private final AccountService accountService;
		private final EmailService emailService;
		private final OtpClient otpClient;
		private final EmailClient emailClient;
		private final AuthenticationManager authenticationManager;
		private static final String OTP_CB = "otpService";
		private final JwtUtil jwtUtil;
		private final OtpService otpService;
		private final UserDetailsService userDetailsService;
	    public AccountController(AuthenticationManager authenticationManager,AccountService accountService
	    		,JwtUtil jwtUtil,OtpClient otpClient,EmailClient emailClient, EmailService emailService, OtpService otpService, UserDetailsService userDetailsService) {
	        this.accountService = accountService;
			this.emailService = emailService;
			this.authenticationManager = authenticationManager;
			this.jwtUtil = jwtUtil;
			this.otpClient = otpClient;
			this.emailClient = emailClient;
			this.otpService = otpService;
			this.userDetailsService = userDetailsService;
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody AccountDto accountDto) {
	        try {
	            Authentication authenticationRequest =
	                    new UsernamePasswordAuthenticationToken(accountDto.getUserName(), accountDto.getPassword());

	            Authentication authenticationResponse =
	                    authenticationManager.authenticate(authenticationRequest);

	            CustomUserDetails userDetails =(CustomUserDetails) authenticationResponse.getPrincipal();
	            
	            OtpDto otp = otpService.generateOtpWithhCircuitBreaker(accountDto.getUserName());
	            if (otp.getRes()!=null && otp.getRes().contains("unavailable")) {
	            	return ResponseEntity
	            	        .status(HttpStatus.SERVICE_UNAVAILABLE) 
	            	        .body(new ErrorResponse("OTP_UNAVAILABLE", "OTP service temporarily unavailable. Please try again later."));
	            }
	            
	            Map<String, Object> model = new HashMap<>();
	            model.put("name",otp.getUserName());
	            model.put("email", otp.getUserName());
	            model.put("subscription", "Premium");
	            model.put("otp",otp.getCodeHash());
	            model.put("bankName","Medicare");
	            
	            EmailRequest req = new EmailRequest(otp.getUserName(),"Your otp for login to Medicare account is : "
	  	      		  +otp.getCodeHash() +".Please do not share it with any one.","OTP Verification",model);
	           
	            String otpResult = emailService.sendOtpWithCircuitBreaker(req);
	            if (otpResult.contains("unavailable") || otpResult.contains("Fallback")) {
	            	return ResponseEntity
	            	        .status(HttpStatus.SERVICE_UNAVAILABLE) 
	            	        .body(new ErrorResponse("OTP_UNAVAILABLE", "Email service temporarily unavailable. Please try again later."));
	            }
	            String jwtToken = jwtUtil.generateToken(accountDto.getUserName(),userDetails.getAuthorities());
	            SuccessfulLoginResponse response = new SuccessfulLoginResponse("Otp has been sent to the verified email.");
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
	    
	    @PostMapping("/changePassword")
	    public PasswordChangeResult changePassword(@RequestBody ChangePasswordRequest req){
	    	return accountService.changePassword(req);
	    }
	    
	    @GetMapping("/validateToken")
	    public AccountResponse validateToken(String token) {
	    	AccountResponse resp = null;
	    	String userName = jwtUtil.extractUsername(token);
	    	UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
	    	if(userName.equals(userDetails.getUsername()) && !jwtUtil.isTokenExpired(token)){
	    		resp = new AccountResponse();
	    		resp.setUsername(userName);
	    		resp.setValidToken(true);
	    	}
	    	return resp;
	    }
	    
	    @PostMapping("/verify-otp")
	    public ResponseEntity<?> verifyOtp(@RequestBody ValidateOtpRequest otpReq) {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(otpReq.getUserName(), otpReq.getOtp()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        CustomUserDetails userDetails =(CustomUserDetails) authentication.getPrincipal();
	        String jwtToken = jwtUtil.generateToken(otpReq.getUserName(),userDetails.getAuthorities());
	        LoginResponse response = new LoginResponse(jwtToken,otpReq.getUserName());
            return ResponseEntity.ok().body(response);
	    }
	    
	   
	    
	    
	

}
