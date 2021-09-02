package com.xenophobe.trellox.dto;

public class UserOutputDto {


    private String message;
    private String token;
    private String email;


    public UserOutputDto(String message,String token,String email){
        this.message=message;
        this.token=token;
        this.email=email;
    }

    public UserOutputDto(String message, String email) {
        this.message = message;
        this.email = email;
    }

    public UserOutputDto(){ }

    public UserOutputDto(String email) {
        this.email = email;
    }

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
                ", email='" + email + '\'' +
                '}';
    }
}
