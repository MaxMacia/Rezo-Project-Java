package com.maxencemacia.Rezo.service.authentication;


import com.maxencemacia.Rezo.entity.payload.request.LoginRequest;
import com.maxencemacia.Rezo.entity.payload.request.SignupRequest;
import com.maxencemacia.Rezo.entity.payload.response.JwtResponse;
import com.maxencemacia.Rezo.entity.payload.response.MessageResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signUpRequest);
}
