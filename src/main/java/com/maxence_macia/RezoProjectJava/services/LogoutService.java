package com.maxence_macia.RezoProjectJava.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.repositories.TokenRepository;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void logout(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
			) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userLogin;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
		userLogin = this.jwtService.extractUsername(jwt);
		
		if (userLogin != null) {
			User user = this.userRepository.findByLogin(userLogin)
					.orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
			
			var tokenList = this.tokenRepository.findAllTokenByUser(user.getId());
			this.tokenRepository.deleteAll(tokenList);
			
			response.setContentType("application/json");
			response.setStatus(HttpStatus.OK.value());
			try {
				response.getOutputStream().println("{ "
						+ "\"status\": \"" + HttpStatus.OK.value() + "\","
						+ "\"message\": \"Succes de la deconnection\"," 
						+ "\"timeStamp\": \"" + System.currentTimeMillis() 
						+  "\" }");
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
	}
}
