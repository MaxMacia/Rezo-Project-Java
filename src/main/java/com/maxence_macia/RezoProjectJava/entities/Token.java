package com.maxence_macia.RezoProjectJava.entities;

public class Token {
	private long id;
	private String token;
	private TokenType tokenType;
	private boolean expired;
	private boolean revoked;
	
	public Token() {}
	public Token(
			String token,
			TokenType tokenType,
			boolean expired,
			boolean revoked
			) {
		super();
		this.token = token;
		this.tokenType = tokenType;
		this.expired = expired;
		this.revoked = revoked;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public TokenType getTokenType() {
		return tokenType;
	}
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public boolean isRevoked() {
		return revoked;
	}
	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}
}
