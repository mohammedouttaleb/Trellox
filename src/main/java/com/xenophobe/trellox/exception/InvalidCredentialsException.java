package com.xenophobe.trellox.exception;

public class InvalidCredentialsException extends UserException{


    public InvalidCredentialsException(String code, String message) {
        super(code, message);
    }

    public InvalidCredentialsException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
