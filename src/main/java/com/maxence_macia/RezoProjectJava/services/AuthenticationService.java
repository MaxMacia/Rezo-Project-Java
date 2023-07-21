package com.maxence_macia.RezoProjectJava.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxence_macia.RezoProjectJava.auth.AuthenticationRequest;
import com.maxence_macia.RezoProjectJava.auth.AuthenticationResponse;
import com.maxence_macia.RezoProjectJava.auth.RegisterRequest;
import com.maxence_macia.RezoProjectJava.entities.Token;
import com.maxence_macia.RezoProjectJava.entities.TokenType;
import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.exceptions.UserAlreadyExistException;
import com.maxence_macia.RezoProjectJava.repositories.TokenRepository;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		var accessToken = this.jwtService.generateAccessToken(user);
		var refreshToken = this.jwtService.generateRefreshToken(user);
		this.saveUserTokens(savedUser, accessToken, refreshToken);
		var response = new AuthenticationResponse();
		response.setStatus(HttpStatus.CREATED.value());
		response.setAccesstoken(accessToken);
		response.setRefreshtoken(refreshToken);
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
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
		var accessToken = this.jwtService.generateAccessToken(user);
		var refreshToken = this.jwtService.generateRefreshToken(user);
		this.revokeAllUserTokens(user);
		this.saveUserTokens(user, accessToken, refreshToken);
		var response = new AuthenticationResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setAccesstoken(accessToken);
		response.setRefreshtoken(refreshToken);
		response.setTimeStamp(System.currentTimeMillis());
		
		return response;
	}
	
	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
			) throws StreamWriteException, DatabindException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String refreshToken;
		final String userLogin;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		refreshToken = authHeader.substring(7);
		userLogin = this.jwtService.extractUsername(refreshToken);
		
		if (userLogin != null) {
			var user = this.userRepository.findByLogin(userLogin)
					.orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
			var isTokenValid = this.tokenRepository.findByToken(refreshToken)
					.map(t -> !t.isExpired() && !t.isRevoked())
					.orElse(false);
			
			if (this.jwtService.isTokenValid(refreshToken, user) && isTokenValid) {
				var accessToken = this.jwtService.generateAccessToken(user);
				this.revokeAllUserTokens(user);
				this.saveUserTokens(user, accessToken, refreshToken);
				var authResponse = new AuthenticationResponse();
				authResponse.setStatus(HttpStatus.OK.value());
				authResponse.setAccesstoken(accessToken);
				authResponse.setRefreshtoken(refreshToken);
				authResponse.setTimeStamp(System.currentTimeMillis());
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);;
			}
			
		}
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

	private void saveUserTokens(
			User user,
			String accessToken,
			String refreshToken
			) {
		this.saveUserToken(user, accessToken, TokenType.BEARER);
		this.saveUserToken(user, refreshToken, TokenType.REFRESH);
	}
	
	private void saveUserToken(User user, String jwtToken, TokenType type) {
		Token token = new Token();
		token.setUser(user);
		token.setToken(jwtToken);
		token.setTokenType(type);
		token.setExpired(false);
		token.setRevoked(false);
		this.tokenRepository.save(token);
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public TokenRepository getTokenRepository() {
		return tokenRepository;
	}
	public void setTokenRepository(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	public JwtService getJwtService() {
		return jwtService;
	}
	public void setJwtService(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
