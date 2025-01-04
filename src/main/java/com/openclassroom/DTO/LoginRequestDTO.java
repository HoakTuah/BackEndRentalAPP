package com.openclassroom.DTO;

public class LoginRequest {
    private String login;
    private String password;

    // Constructeurs
    public LoginRequest() {
    }

    // Getters et Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}