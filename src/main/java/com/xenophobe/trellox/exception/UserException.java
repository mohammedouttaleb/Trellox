package com.xenophobe.trellox.exception;



public class UserException extends RuntimeException {

    private final String code;
    private final String msg;

    public UserException(String code, String message) {
        super(message);
        this.msg=message;
        this.code=code;
    }

    public UserException(String code, String message, Throwable cause) {
        super(message, cause);
        this.msg=message;
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
