package com.maxence_macia.RezoProjectJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxence_macia.RezoProjectJava.auth.AuthenticationRequest;
import com.maxence_macia.RezoProjectJava.auth.AuthenticationResponse;
import com.maxence_macia.RezoProjectJava.auth.RegisterRequest;
import com.maxence_macia.RezoProjectJava.entities.Token;
import com.maxence_macia.RezoProjectJava.entities.TokenType;
import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.exceptions.UserAlreadyExistException;
import com.maxence_macia.RezoProjectJava.repositories.TokenRepository;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
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
		
		var userInDB = this.userRepository.findByLogin(user.getLogin()).orElse(null);
		if (userInDB != null) throw new UserAlreadyExistException("L'utilisateur existe déjà");
		
		var savedUser = this.userRepository.save(user);
		var jwtToken = this.jwtService.generateToken(user);
		this.saveUserToken(savedUser, jwtToken);
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
		var user = this.userRepository.findByLogin(request.getLogin())
				.orElseThrow(() -> new UserAlreadyExistException("L'utilisateur existe déjà"));
		var jwtToken = this.jwtService.generateToken(user);
		this.revokeAllUserTokens(user);
		this.saveUserToken(user, jwtToken);
		var response = new AuthenticationResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setToken(jwtToken);
		response.setTimeStamp(System.currentTimeMillis());
		
		return response;
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserTokens = this.tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty()) {
			return;
		}
		validUserTokens.forEach(
				t -> {
					t.setExpired(true);
					t.setRevoked(true);
				}
				);
		this.tokenRepository.saveAll(validUserTokens);
	}

	private void saveUserToken(User user, String jwtToken) {
		Token token = new Token();
		token.setUser(user);
		token.setToken(jwtToken);
		token.setTokenType(TokenType.Bearer);
		token.setExpired(false);
		token.setRevoked(false);
		this.tokenRepository.save(token);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
