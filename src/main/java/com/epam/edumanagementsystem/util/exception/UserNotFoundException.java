package com.epam.edumanagementsystem.util.exception;

public class UserNotFoundException extends NullPointerException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String s) {
        super(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
