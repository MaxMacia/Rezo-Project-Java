package com.maxence_macia.RezoProjectJava.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maxence_macia.RezoProjectJava.entities.*;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repository;
	private User usr;

	@BeforeAll
	public void initTest() {
		User user = new User("user1", "user1@mail.com", "1234", Role.USER);
		this.usr = this.repository.save(user);
	}
	
	@Test
	public void testFindUserByLogin() {
		User user = this.repository.findByLogin("user1").orElse(null);
		assertThat(user).isNotNull();
		assertThat(user.getLogin()).isEqualTo(usr.getLogin());
	}
	

	@AfterAll
	public void finishTest() {
		this.repository.delete(usr);
		usr = null;
		
		User user = repository.findByLogin("user1").orElse(null);
		assertThat(user).isNull();
		
	}

	public User getUsr() {
		return this.usr;
	}
	public void setUsr(User usr) {
		this.usr = usr;
	}
}
