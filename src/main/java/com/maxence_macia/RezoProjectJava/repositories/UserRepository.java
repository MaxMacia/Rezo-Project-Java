package com.maxence_macia.RezoProjectJava.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxence_macia.RezoProjectJava.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByLogin(String login);
}
