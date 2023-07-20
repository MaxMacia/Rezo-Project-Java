package com.maxence_macia.RezoProjectJava.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.maxence_macia.RezoProjectJava.auth.AuthenticationRequest;
import com.maxence_macia.RezoProjectJava.auth.AuthenticationResponse;
import com.maxence_macia.RezoProjectJava.auth.RegisterRequest;
import com.maxence_macia.RezoProjectJava.entities.Role;
import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticationServiceTest {
	@Autowired
	private UserRepository repository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationService authenticationService;
	private User usr;
	private String token;
	private AuthenticationResponse createdResponse;
	private AuthenticationResponse okResponse;


	@BeforeAll
	public void initTest() {
		User user = new User("user1", "user1@mail.com", "1234", Role.USER);
		this.usr = repository.save(user);
		this.token = this.jwtService.generateToken(usr);
		this.createdResponse = new AuthenticationResponse(
				HttpStatus.CREATED.value(),
				token,
				System.currentTimeMillis()
				);
		this.okResponse = new AuthenticationResponse(
				HttpStatus.OK.value(),
				token,
				System.currentTimeMillis()
				);
	}
	
	@Test
	public void registerTest() {
		this.repository.delete(this.usr);
		RegisterRequest request = new RegisterRequest("user1", "user1@mail.com", "1234", Role.USER);
		var response = this.authenticationService.register(request);
		
		assertThat(response.getStatus()).isEqualTo(createdResponse.getStatus());
		assertThat(response.getToken()).isEqualTo(createdResponse.getToken());
		
		this.usr = repository.findByLogin(request.getLogin()).orElse(null);
	}
	
	@Test
	public void authenticateTest() {
		AuthenticationRequest request = new AuthenticationRequest("user1", "1234");
		var response = this.authenticationService.authenticate(request);
		
		assertThat(response.getStatus()).isEqualTo(okResponse.getStatus());
		assertThat(response.getToken()).isEqualTo(okResponse.getToken());
		
		usr = repository.findByLogin(request.getLogin()).orElse(null);
	}
	
	
	
	@AfterAll
	public void finishTest() {
		this.repository.delete(usr);
		this.usr = null;
		this.token = null;
		this.createdResponse = null;
		this.okResponse = null;
		
		User user = repository.findByLogin("user1").orElse(null);
		assertThat(user).isNull();
		
	}


	public User getUsr() {
		return this.usr;
	}
	public void setUsr(User usr) {
		this.usr = usr;
	}
	public String getToken() {
		return this.token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public AuthenticationResponse getCreatedResponse() {
		return this.createdResponse;
	}
	public void setCreatedResponse(AuthenticationResponse createdResponse) {
		this.createdResponse = createdResponse;
	}
	public AuthenticationResponse getOkResponse() {
		return this.okResponse;
	}
	public void setOkResponse(AuthenticationResponse okResponse) {
		this.okResponse = okResponse;
	}
}
