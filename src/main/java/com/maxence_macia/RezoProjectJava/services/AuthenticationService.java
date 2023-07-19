package com.maxence_macia.RezoProjectJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.maxence_macia.RezoProjectJava.auth.AuthenticationRequest;
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
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = new User();
		user.setLogin(request.getLogin());
		user.setEmail(request.getEmail());
		user.setPassword(this.passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		
		var userInDB = this.repository.findByLogin(user.getLogin()).orElse(null);
		if (userInDB != null) throw new UserAlreadyExistException("L'utilisateur existe déjà");
		
		this.repository.save(user);
		var jwtToken = this.jwtService.generateToken(user);
		var response = new AuthenticationResponse();
		response.setStatus(HttpStatus.CREATED.value());
		response.setToken(jwtToken);
		response.setTimeStamp(System.currentTimeMillis());
		
		return response;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getLogin(),
						request.getPassword()
						)
				);
		var user = this.repository.findByLogin(request.getLogin())
				.orElseThrow(() -> new UserAlreadyExistException("L'utilisateur existe déjà"));
		var jwtToken = this.jwtService.generateToken(user);
		var response = new AuthenticationResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setToken(jwtToken);
		response.setTimeStamp(System.currentTimeMillis());
		
		return response;
	}

}
