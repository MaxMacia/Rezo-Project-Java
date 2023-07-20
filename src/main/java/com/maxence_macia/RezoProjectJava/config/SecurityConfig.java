package com.maxence_macia.RezoProjectJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private LogoutHandler logoutHandler;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(
					authorize -> authorize
						.requestMatchers("/api/auth/**").permitAll()
						.anyRequest().authenticated()
					)
			.sessionManagement(
					management -> management
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.logout(
					logout -> logout
						.logoutUrl("/api/auth/logout")
						.addLogoutHandler(this.logoutHandler)
						.logoutSuccessHandler(
								(request, response, authentification) -> SecurityContextHolder.clearContext()
								)
					)
			.exceptionHandling(
					exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(this.authenticationEntryPoint)
						.accessDeniedHandler(this.accessDeniedHandler)
					);
		
		return http.build();
	}
}
