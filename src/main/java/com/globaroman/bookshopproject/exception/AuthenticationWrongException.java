package com.globaroman.bookshopproject.exception;

public class AuthenticationWrongException extends RuntimeException {

    public AuthenticationWrongException(String message) {
        super(message);
    }

    public AuthenticationWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}
