package com.maxence_macia.RezoProjectJava.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maxence_macia.RezoProjectJava.entities.Role;
import com.maxence_macia.RezoProjectJava.entities.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class JwtServiceTest {
	@Autowired
	private JwtService jwtService;
	private User user;
	private String accessToken;
	private String refreshToken;

	@BeforeAll
	public void initTest() {
		this.user = new User("user1", "user1@mail.com", "1234", Role.USER);
		this.accessToken = this.jwtService.generateAccessToken(user);
		this.refreshToken = this.jwtService.generateRefreshToken(user);
	}
	
	@Test
	public void generateAccessTokenTest() {
		var token = this.jwtService.generateAccessToken(this.user);
		
		assertThat(token.substring(0, 20)).isEqualTo(this.accessToken.substring(0, 20));
	}
	
	@Test
	public void generateRefreshTokenTest() {
		var token = this.jwtService.generateRefreshToken(this.user);
		
		assertThat(token.substring(0, 20)).isEqualTo(this.refreshToken.substring(0, 20));
	}
	
	@Test
	public void extractNameTest() {
		String username = this.jwtService.extractUsername(this.accessToken);
		assertThat(username).isEqualTo(user.getLogin());
		
		username = this.jwtService.extractUsername(this.refreshToken);
		assertThat(username).isEqualTo(user.getLogin());
	}
	
	@Test
	public void isTokenValidTest() {
		boolean bool = this.jwtService.isTokenValid(this.accessToken, this.user);
		assertThat(bool).isTrue();
		
		bool = this.jwtService.isTokenValid(this.refreshToken, this.user);
		assertThat(bool).isTrue();
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
