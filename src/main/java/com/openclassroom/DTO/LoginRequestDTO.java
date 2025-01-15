package com.openclassroom.DTO;

public class LoginRequestDTO {
    private String email;
    private String password;

    // ----------------------------------------------------------------------------------------
    // Getters => Return the value of the property
    // ----------------------------------------------------------------------------------------

    public String getemail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // ----------------------------------------------------------------------------------------
    // Setters => Set the value of the property
    // ----------------------------------------------------------------------------------------

    public void setLogin(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}