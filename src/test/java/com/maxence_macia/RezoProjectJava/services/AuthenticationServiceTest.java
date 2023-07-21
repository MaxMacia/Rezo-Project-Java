package com.maxence_macia.RezoProjectJava.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
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
import com.maxence_macia.RezoProjectJava.repositories.TokenRepository;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticationServiceTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationService authenticationService;
	private User usr;
	private String accessToken;
	private String refreshToken;
	private AuthenticationResponse response;
	
	@Test
	public void registerTest() {
		User user = new User("user1", "user1@mail.com", "1234", Role.USER);
		RegisterRequest request = new RegisterRequest();
		request.setLogin(user.getLogin());
		request.setEmail(user.getEmail());
		request.setPassword(user.getPassword());
		request.setRole(user.getRole());
		
		var response = this.authenticationService.register(request);
		
		this.usr = this.userRepository.findByLogin(user.getLogin()).orElse(null);
		this.accessToken = this.jwtService.generateAccessToken(usr);
		this.refreshToken = this.jwtService.generateRefreshToken(usr);
		this.response = new AuthenticationResponse(
				HttpStatus.CREATED.value(),
				this.accessToken,
				this.refreshToken,
				System.currentTimeMillis()
				);
	
		assertThat(response.getStatus()).isEqualTo(this.response.getStatus());
		assertThat(response.getAccesstoken()).isEqualTo(this.response.getAccesstoken());
		assertThat(response.getRefreshtoken()).isEqualTo(this.response.getRefreshtoken());
		
		assertThat(this.usr.getTokens()).isNotEmpty();
	}
	
	@Test
	public void authenticateTest() {
		this.response = new AuthenticationResponse(
				HttpStatus.OK.value(),
				this.accessToken,
				this.refreshToken,
				System.currentTimeMillis()
				);
		
		AuthenticationRequest request = new AuthenticationRequest("user1", "1234");
		var response = this.authenticationService.authenticate(request);
		
		assertThat(response.getStatus()).isEqualTo(this.response.getStatus());
		assertThat(response.getAccesstoken()).isEqualTo(this.response.getAccesstoken());
		assertThat(response.getRefreshtoken()).isEqualTo(this.response.getRefreshtoken());
		
		assertThat(this.tokenRepository.findAllTokenByUser(this.usr.getId()).size()).isGreaterThan(1);
	}
	
	
	
	@AfterAll
	public void finishTest() {
		
		var tokens = this.tokenRepository.findAllTokenByUser(this.usr.getId());
		tokens.forEach(
				t -> t.setUser(null)
				);
		this.tokenRepository.saveAll(tokens);
		this.tokenRepository.deleteAll();
		this.usr.setTokens(null);
		this.userRepository.delete(usr);
		this.usr = null;
		this.accessToken = null;
		this.refreshToken = null;
		this.response = null;
		
		User user = this.userRepository.findByLogin("user1").orElse(null);
		assertThat(user).isNull();
		
	}


	public User getUsr() {
		return this.usr;
	}
	public void setUsr(User usr) {
		this.usr = usr;
	}
	public AuthenticationResponse getResponse() {
		return this.response;
	}
	public void setResponse(AuthenticationResponse response) {
		this.response = response;
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
