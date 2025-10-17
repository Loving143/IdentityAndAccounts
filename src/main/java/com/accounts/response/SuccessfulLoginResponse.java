package com.accounts.response;

public class SuccessfulLoginResponse {
	
	private String data;

	public SuccessfulLoginResponse(String message) {
		this.data = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

}
