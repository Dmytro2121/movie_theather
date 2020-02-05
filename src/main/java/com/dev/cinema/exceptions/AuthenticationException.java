package com.dev.cinema.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Exception e) {
        super(message, e);
    }

    public AuthenticationException(String message) {
    }
}
