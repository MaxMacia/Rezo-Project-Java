package com.maxence_macia.RezoProjectJava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice
public class ClassExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exception) {
		var response = new ExceptionResponse(
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage(),
				System.currentTimeMillis()
				);
		var entity = new ResponseEntity<ExceptionResponse>(
				response,
				HttpStatus.NOT_FOUND
				);
		
		return entity;
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleException(UserAlreadyExistException exception) {
		var response = new ExceptionResponse(
				HttpStatus.CONFLICT.value(),
				exception.getMessage(),
				System.currentTimeMillis()
				);
		var entity = new ResponseEntity<ExceptionResponse>(
				response,
				HttpStatus.CONFLICT
				);
		
		return entity;
	}
}
