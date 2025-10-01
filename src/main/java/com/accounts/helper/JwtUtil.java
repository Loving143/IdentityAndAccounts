package com.accounts.helper;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

	@Component
	public class JwtUtil {

	    private final String SECRET_KEY = "ZGe7eDBd1lSvPy8peQZjISBZIc/x40SBwlS5omD7Hlk=";
	    // Use environment variables for secret keys in production

	    // Generate JWT Token
	    public String generateToken(String username , Collection<? extends GrantedAuthority>authorities) {
	    	 List<String> authorityList = authorities.stream()
                     .map(GrantedAuthority::getAuthority)
                     .collect(Collectors.toList());
	    	return Jwts.builder()
	                .setSubject(username)
	                .claim("authorities",authorityList)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 60 * 10)) // 10 hours
	                .signWith(SignatureAlgorithm.HS256,SECRET_KEY )
	                
	                .compact();
	    }

	    // Validate the JWT Token
	    public String extractUsername(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	    }

		public boolean validateToken(String token, CustomUserDetails userDetails) {
			 String username = extractUsername(token);
		     return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
		
		private boolean isTokenExpired(String token) {
	        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
	        return expiration.before(new Date());
	    }
	}
