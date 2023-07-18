package com.maxence_macia.RezoProjectJava.entities;

public class User {
	private long id;
	private String login;
	private String email;
	private String password;
	
	public User() {}
	public User(
			String login,
			String email,
			String password
			) {
		super();
		this.login = login;
		this.email = email;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
