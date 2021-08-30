package com.xenophobe.trellox.exception;

public class ListNotFoundException extends ListException {


    public ListNotFoundException(String code, String message) {
        super(code, message);
    }

    public ListNotFoundException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
