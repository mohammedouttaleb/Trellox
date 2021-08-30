package com.xenophobe.trellox.exception;

public class CardNotFoundException extends CardException {

    public CardNotFoundException(String code, String message) {
        super(code, message);
    }

    public CardNotFoundException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
