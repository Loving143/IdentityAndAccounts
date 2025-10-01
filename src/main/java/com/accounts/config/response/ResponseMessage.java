package com.accounts.config.response;

public class ResponseMessage<T> {
	
	private String status;
	private T data;
	
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ResponseMessage(String status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
	
	public ResponseMessage( T data) {
		super();
		this.status = "200";
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
