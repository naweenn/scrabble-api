package com.scrabble.api.exception;

public class UserInputValidationException extends RuntimeException {

    public UserInputValidationException(String message) {
        super(message);
    }

}
