package com.maxence_macia.RezoProjectJava.entities;

import jakarta.persistence.*;

@Entity
@Table(name="tokens")
public class Token {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="token")
	private String token;
	@Enumerated(EnumType.STRING)
	@Column(name="token_type")
	private TokenType tokenType;
	@Column(name="expired")
	private boolean expired;
	@Column(name="revoked")
	private boolean revoked;
	@ManyToOne
	@JoinColumn(name="users_id")
	private User user;
	
	public Token() {}
	public Token(
			String token,
			TokenType tokenType,
			boolean expired,
			boolean revoked,
			User user
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
