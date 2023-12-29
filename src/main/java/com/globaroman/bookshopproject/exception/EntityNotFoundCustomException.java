package com.globaroman.bookshopproject.exception;

public class EntityNotFoundCustomException extends RuntimeException {
    public EntityNotFoundCustomException(String message) {
        super(message);
    }
}
