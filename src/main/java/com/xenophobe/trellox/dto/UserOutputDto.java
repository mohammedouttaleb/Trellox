package com.xenophobe.trellox.dto;

public class UserOutputDto {


    private String message;
    private String token;

    public UserOutputDto(String message,String token){
        this.message=message;
        this.token=token;
    }
    public UserOutputDto(){ }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "UserOutputDto{" +
                "message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
