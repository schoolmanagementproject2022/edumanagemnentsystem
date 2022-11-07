package com.epam.edumanagementsystem.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity not found exception.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
