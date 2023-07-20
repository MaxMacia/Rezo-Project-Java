package com.maxence_macia.RezoProjectJava.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class ApplicationConfig {
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Autowired
			private UserRepository repository;

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return this.repository.findByLogin(username)
						.orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));
			}
			
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.userDetailsService());
		authProvider.setPasswordEncoder(this.passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {

			@Override
			public void commence(
					HttpServletRequest request,
					HttpServletResponse response,
					AuthenticationException authException
					) throws IOException, ServletException {
				response.setContentType("application/json");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getOutputStream().println("{ "
						+ "\"status\": \"" + HttpStatus.UNAUTHORIZED.value() + "\","
						+ "\"message\": \"Token invalide, veuillez vous identifier\"," 
						+ "\"timeStamp\": \"" + System.currentTimeMillis() 
						+  "\" }");
			}
			
		};
	}
	
	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {

			@Override
			public void handle(
					HttpServletRequest request,
					HttpServletResponse response,
					AccessDeniedException accessDeniedException
					) throws IOException, ServletException {
				response.setContentType("application/json");
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.getOutputStream().println("{ "
						+ "\"status\": \"" + HttpStatus.UNAUTHORIZED.value() + "\","
						+ "\"message\": \"Accès non authorisé\"," 
						+ "\"timeStamp\": \"" + System.currentTimeMillis() 
						+  "\" }");
			}
			
		};
	}
}
