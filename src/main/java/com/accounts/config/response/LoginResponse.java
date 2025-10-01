package com.accounts.config.response;

public class LoginResponse {
	
	private String token;
	private String type = "Bearer";
	private long expiresIn; 
	private String username;

	public LoginResponse(String jwtToken,String userName) { 
		this.token = jwtToken;
		this.username = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
