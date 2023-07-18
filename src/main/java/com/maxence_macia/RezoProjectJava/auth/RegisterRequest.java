package com.maxence_macia.RezoProjectJava.auth;

import com.maxence_macia.RezoProjectJava.entities.Role;


public class RegisterRequest {
	private String login;
	private String email;
	private String password;
	private Role role;
	
	public RegisterRequest() {}
	public RegisterRequest(
			String login,
			String email,
			String password,
			Role role
			) {
		super();
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
