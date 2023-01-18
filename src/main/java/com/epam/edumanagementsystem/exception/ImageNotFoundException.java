package com.epam.edumanagementsystem.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException() {
        super("There is a problem with the image. Please try again later.");
    }

    public ImageNotFoundException(String message) {
        super(message);
    }
}
