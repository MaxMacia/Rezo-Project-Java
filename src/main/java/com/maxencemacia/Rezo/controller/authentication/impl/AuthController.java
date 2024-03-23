package com.maxencemacia.Rezo.controller.authentication.impl;

import com.maxencemacia.Rezo.controller.authentication.AuthApi;
import com.maxencemacia.Rezo.entity.payload.request.LoginRequest;
import com.maxencemacia.Rezo.entity.payload.request.SignupRequest;
import com.maxencemacia.Rezo.entity.payload.response.JwtResponse;
import com.maxencemacia.Rezo.entity.payload.response.MessageResponse;
import com.maxencemacia.Rezo.service.authentication.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController implements AuthApi {
    AuthService authService;

    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @Override
    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) {
        return new ResponseEntity<>(authService.registerUser(signUpRequest), HttpStatus.CREATED);
    }
}
