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
	private static User user;
	private static String jwtToken;

	@BeforeAll
	public void initTest() {
		user = new User("user1", "user1@mail.com", "1234", Role.USER);
		jwtToken = this.jwtService.generateToken(user);
	}
	
	@Test
	public void generateTokenTest() {
		var token = this.jwtService.generateToken(user);
		
		assertThat(token).isEqualTo(jwtToken);
	}
	
	@Test
	public void extractNameTest() {
		String username = this.jwtService.extractUsername(jwtToken);
		
		assertThat(username).isEqualTo(user.getLogin());
	}
	
	@Test
	public void isTokenValidTest() {
		boolean bool = this.jwtService.isTokenValid(jwtToken, user);
		
		assertThat(bool).isTrue();
	}

	public static String getJwtToken() {
		return jwtToken;
	}
	public static void setJwtToken(String jwtToken) {
		JwtServiceTest.jwtToken = jwtToken;
	}
}
