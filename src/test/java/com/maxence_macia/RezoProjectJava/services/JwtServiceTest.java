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
	private String jwtToken;

	@BeforeAll
	public void initTest() {
		this.user = new User("user1", "user1@mail.com", "1234", Role.USER);
		this.jwtToken = this.jwtService.generateToken(user);
	}
	
	@Test
	public void generateTokenTest() {
		var token = this.jwtService.generateToken(this.user);
		
		assertThat(token).isEqualTo(this.jwtToken);
	}
	
	@Test
	public void extractNameTest() {
		String username = this.jwtService.extractUsername(this.jwtToken);
		
		assertThat(username).isEqualTo(user.getLogin());
	}
	
	@Test
	public void isTokenValidTest() {
		boolean bool = this.jwtService.isTokenValid(this.jwtToken, this.user);
		
		assertThat(bool).isTrue();
	}

	public String getJwtToken() {
		return this.jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
