package com.xenophobe.trellox.exception;

public class EmailAlreadyExistsException extends UserException {

    public EmailAlreadyExistsException(String code, String message) {
        super(code, message);
    }

    public EmailAlreadyExistsException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
