package com.maxence_macia.RezoProjectJava.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maxence_macia.RezoProjectJava.entities.Message;
import com.maxence_macia.RezoProjectJava.entities.User;
import com.maxence_macia.RezoProjectJava.messages.MessageRequest;
import com.maxence_macia.RezoProjectJava.messages.MessageResponse;
import com.maxence_macia.RezoProjectJava.repositories.MessageRepository;
import com.maxence_macia.RezoProjectJava.repositories.UserRepository;

@Service
public class MessageService {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageRepository messageRepository;

	public MessageResponse create(String authHeader, MessageRequest request) {
		var user = this.getAuthenticatedUser(authHeader);
		
		if (user != null) {
			var message = new Message();
			message.setUser(user);
			message.setContent(request.getContent());
			message.setLikes(0);
			message.setUsersLiked(null);
			
			var savedMessage = this.messageRepository.save(message);
			
			return new MessageResponse(
					HttpStatus.CREATED.value(),
					user.getLogin(),
					savedMessage.getContent(),
					System.currentTimeMillis()
					);
		} else {
			return null;
		}
	}
	
	private User getAuthenticatedUser(String authHeader) {
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}
		
		final String jwt = authHeader.substring(7);
		final String userLogin = this.jwtService.extractUsername(jwt);
		
		if (userLogin != null) {
			var user = this.userRepository.findByLogin(userLogin)
					.orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
			
			return user;

			} else {
				return null;
			}
	}
}
