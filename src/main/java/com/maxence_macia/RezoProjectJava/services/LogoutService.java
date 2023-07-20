package com.maxence_macia.RezoProjectJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.maxence_macia.RezoProjectJava.repositories.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public void logout(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
			) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
		var storedToken = this.tokenRepository.findByToken(jwt).orElse(null);
		
		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			this.tokenRepository.save(storedToken);
		}		
	}
}
