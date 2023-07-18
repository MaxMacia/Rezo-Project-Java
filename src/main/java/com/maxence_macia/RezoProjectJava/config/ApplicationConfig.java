package com.maxence_macia.RezoProjectJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

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
}
