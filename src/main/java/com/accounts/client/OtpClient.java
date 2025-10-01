package com.accounts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.accounts.dto.OtpDto;

@FeignClient(name = "otp-service", url = "http://localhost:8042/otp")
	public interface OtpClient {

	    @PostMapping("/generate")
	    OtpDto generateOtp(@RequestParam("username") String username);

	    @PostMapping("/validate")
	    boolean validateOtp(@RequestParam("username") String username,
	                        @RequestParam("otp") String otp);
	}

