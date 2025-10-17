package com.accounts.helper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.accounts.client.OtpClient;

public class OtpAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private OtpClient otpClient;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		logger.info("Otp provider executed");;
        String username = authentication.getName();
        String otpCode = authentication.getCredentials().toString();

        if (otpClient.validateOtp(username, otpCode)) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),new ArrayList<>());
        } else {
            throw new BadCredentialsException("Invalid OTP");
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
