package com.accounts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accounts.client.OtpClient;
import com.accounts.dto.OtpDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OtpService {
	
	@Autowired
	OtpClient otpClient;
	private static final String OTP_CB = "otpService";
	
	@CircuitBreaker(name = OTP_CB, fallbackMethod = "otpFallback")
	public OtpDto generateOtpWithhCircuitBreaker(String userName) {
		return otpClient.generateOtp(userName);
	}

	public OtpDto otpFallback(String userName, Throwable ex) {
		OtpDto otp = new OtpDto();
		otp.setRes("OTP service temporarily unavailable. OTP could not be sent.");
		return otp;
	}

}
