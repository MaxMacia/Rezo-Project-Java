package com.maxence_macia.RezoProjectJava.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maxence_macia.RezoProjectJava.entities.Role;
import com.maxence_macia.RezoProjectJava.entities.Token;
import com.maxence_macia.RezoProjectJava.entities.TokenType;
import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.services.JwtService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TokenRepositoryTest {
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	
	private User usr;
	private String jwtToken;
	private Token tokn;
	
	@BeforeAll
	public void initTest() {
		User user = new User("user1", "user1@mail.com", "1234", Role.USER);
		this.usr = this.userRepository.save(user);
		this.jwtToken = this.jwtService.generateToken(usr);
		Token token = new Token(jwtToken, TokenType.Bearer, false, false, this.usr);
		this.tokn = this.tokenRepository.save(token);
	}
	
	@Test
	public void findAllValidTokenByUserTest() {
		List<Token> tokens = this.tokenRepository.findAllValidTokenByUser(usr.getId());
		
		assertThat(tokens).isNotEmpty();
	}
	
	@Test
	public void findByTokenTest() {
		Token token = this.tokenRepository.findByToken(this.jwtToken).orElse(null);
		
		assertThat(token).isNotNull();
		assertThat(token.getToken()).isEqualTo(this.tokn.getToken());
	}
	
	@AfterAll
	public void finishTest() {
		this.tokn.setUser(null);
		this.tokenRepository.save(this.tokn);
		this.userRepository.delete(this.usr);
		this.tokenRepository.delete(this.tokn);
		this.usr = null;
		this.jwtToken = null;
		this.tokn = null;
	}
	
	public User getUsr() {
		return this.usr;
	}
	public void setUsr(User usr) {
		this.usr = usr;
	}
	public String getJwtToken() {
		return this.jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public Token getTokn() {
		return this.tokn;
	}
	public void setTokn(Token tokn) {
		this.tokn = tokn;
	}
}
