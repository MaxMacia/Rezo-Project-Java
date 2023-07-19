package com.maxence_macia.RezoProjectJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxence_macia.RezoProjectJava.auth.AuthenticationRequest;
import com.maxence_macia.RezoProjectJava.auth.AuthenticationResponse;
import com.maxence_macia.RezoProjectJava.auth.RegisterRequest;
import com.maxence_macia.RezoProjectJava.services.AuthenticationService;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		var response = this.service.register(request);
		var entity = new ResponseEntity<AuthenticationResponse>(
				response,
				HttpStatus.CREATED
				);
		
		return entity;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		var response = this.service.authenticate(request);
		var entity = new ResponseEntity<AuthenticationResponse>(
				response,
				HttpStatus.OK
				);
		
		return entity;
	}
}
