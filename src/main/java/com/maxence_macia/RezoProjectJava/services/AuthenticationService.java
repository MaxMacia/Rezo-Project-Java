package com.maxence_macia.RezoProjectJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.maxence_macia.RezoProjectJava.auth.AuthenticationResponse;
import com.maxence_macia.RezoProjectJava.auth.RegisterRequest;
import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.exceptions.UserAlreadyExistException;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

public class AuthenticationService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository repository;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = new User();
		user.setLogin(request.getLogin());
		user.setEmail(request.getEmail());
		user.setPassword(this.passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		
		var userInDB = this.repository.findByLogin(user.getLogin()).orElse(null);
		if (userInDB != null) throw new UserAlreadyExistException("L'utilisateur existe déjà");
		
		var savedUser = this.repository.save(user);
		
		return null;
	}

}
