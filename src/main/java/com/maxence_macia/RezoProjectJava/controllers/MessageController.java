package com.maxence_macia.RezoProjectJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxence_macia.RezoProjectJava.messages.MessageRequest;
import com.maxence_macia.RezoProjectJava.messages.MessageResponse;
import com.maxence_macia.RezoProjectJava.services.MessageService;


@RestController
@RequestMapping("api/messages")
public class MessageController {
	@Autowired
	private MessageService service;
	
	@PostMapping
	public ResponseEntity<MessageResponse> create(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody MessageRequest request
			) {
		var response = this.service.create(authHeader, request);
		var entity = new ResponseEntity<MessageResponse>(
				response,
				HttpStatus.CREATED
				);
		return entity;
	}
}
