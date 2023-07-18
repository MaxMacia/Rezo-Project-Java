package com.maxence_macia.RezoProjectJava.auth;

public class AuthenticationResponse {
	private int status;
	private String token;
	private long timeStamp;
	
	public AuthenticationResponse() {}
	public AuthenticationResponse(int status, String token, long timeStamp) {
		super();
		this.status = status;
		this.token = token;
		this.timeStamp = timeStamp;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
