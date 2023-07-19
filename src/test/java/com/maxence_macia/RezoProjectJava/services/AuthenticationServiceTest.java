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
	private static User usr;
	private static String token;
	private static AuthenticationResponse createdResponse;
	private static AuthenticationResponse okResponse;


	@BeforeAll
	public void initTest() {
		User user = new User("user1", "user1@mail.com", "1234", Role.USER);
		User savedUser = repository.save(user);
		usr = savedUser;
		token = this.jwtService.generateToken(usr);
		createdResponse = new AuthenticationResponse(
				HttpStatus.CREATED.value(),
				token,
				System.currentTimeMillis()
				);
		setOkResponse(new AuthenticationResponse(
				HttpStatus.OK.value(),
				token,
				System.currentTimeMillis()
				));
	}
	
	@Test
	public void registerTest() {
		repository.delete(usr);
		RegisterRequest request = new RegisterRequest("user1", "user1@mail.com", "1234", Role.USER);
		var response = this.authenticationService.register(request);
		
		assertThat(response.getStatus()).isEqualTo(createdResponse.getStatus());
		assertThat(response.getToken()).isEqualTo(createdResponse.getToken());
		
		usr = repository.findByLogin(request.getLogin()).orElse(null);
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
		repository.delete(usr);
		usr = null;
		token = null;
		createdResponse = null;
		okResponse = null;
		
		User user = repository.findByLogin("user1").orElse(null);
		assertThat(user).isNull();
		
	}


	public static User getUsr() {
		return usr;
	}
	public static void setUsr(User usr) {
		AuthenticationServiceTest.usr = usr;
	}
	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		AuthenticationServiceTest.token = token;
	}
	public static AuthenticationResponse getCreatedResponse() {
		return createdResponse;
	}
	public static void setCreatedResponse(AuthenticationResponse createdResponse) {
		AuthenticationServiceTest.createdResponse = createdResponse;
	}
	public static AuthenticationResponse getOkResponse() {
		return okResponse;
	}
	public static void setOkResponse(AuthenticationResponse okResponse) {
		AuthenticationServiceTest.okResponse = okResponse;
	}
}
