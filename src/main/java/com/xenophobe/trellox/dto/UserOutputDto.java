package com.xenophobe.trellox.dto;

public class UserOutputDto {


    private String message;

    public UserOutputDto(String message){ this.message=message;}
    public UserOutputDto(){ }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserOutputDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
