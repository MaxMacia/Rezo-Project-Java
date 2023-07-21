package com.maxence_macia.RezoProjectJava.auth;

public class AuthenticationResponse {
	private int status;
	private String accesstoken;
	private String refreshtoken;
	private long timeStamp;
	
	public AuthenticationResponse() {}
	public AuthenticationResponse(int status, String accesstoken, String refreshtoken, long timeStamp) {
		super();
		this.status = status;
		this.accesstoken = accesstoken;
		this.refreshtoken = refreshtoken;
		this.timeStamp = timeStamp;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getRefreshtoken() {
		return refreshtoken;
	}
	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
