package com.aws.ccamilo.com.app.useapiserveless.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
