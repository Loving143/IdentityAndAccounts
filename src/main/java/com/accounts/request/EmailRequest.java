package com.accounts.request;

import java.util.HashMap;
import java.util.Map;

public class EmailRequest {

	private String userName;
	private String otp;
	private HashMap<String , Object>model;
	private String recipient;
	private String body;
	private String subject;
	
	public EmailRequest(String recipient, String body, String subject, Map<String, Object> model) {
	this.recipient = recipient;
	this.body = body;
	this.subject = subject;
	this.model = (HashMap<String, Object>) model;
	}
	public String getName() {
		return userName;
	}
	public void setName(String name) {
		this.userName = name;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public HashMap<String, Object> getModel() {
		return model;
	}
	public void setModel(HashMap<String, Object> model) {
		this.model = model;
	}
	
	
}
