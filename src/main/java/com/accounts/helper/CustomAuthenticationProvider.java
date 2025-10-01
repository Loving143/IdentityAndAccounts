package com.accounts.helper;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailsService userDetailsService;
	CustomAuthenticationProvider(CustomUserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder){
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		if(passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		}else {
			throw new BadCredentialsException("Invalid username or password!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
	    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
