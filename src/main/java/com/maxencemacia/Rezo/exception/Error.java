package com.maxencemacia.Rezo.exception;


import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
public enum Error {
    USERNAME_ALREADY_TAKEN("Username already taken", HttpStatus.SC_CONFLICT),
    EMAIL_ALREADY_TAKEN("Email already taken", HttpStatus.SC_CONFLICT),
    USER_NOT_FOUND("User not found", HttpStatus.SC_NOT_FOUND),
    ROLE_NOT_FOUND("Role not found", HttpStatus.SC_NOT_FOUND),
    BAD_REQUEST("bad request", HttpStatus.SC_BAD_REQUEST);
    private final String message;
    private final int status;
    Error(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
