package com.openclassroom.DTO;

import jakarta.validation.constraints.NotEmpty;

public class RegisterRequestDTO {
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------

    public RegisterRequestDTO() {
    }

    // ----------------------------------------------------------------------------------------
    // Getters => Return the value of the property
    // ----------------------------------------------------------------------------------------

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // ----------------------------------------------------------------------------------------
    // Setters => Set the value of the property
    // ----------------------------------------------------------------------------------------

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : null;
    }

}
