package com.xenophobe.trellox.exception;

public class UserNotFoundException extends UserException {


    public UserNotFoundException(String code, String message) {
        super(code, message);
    }

    public UserNotFoundException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
