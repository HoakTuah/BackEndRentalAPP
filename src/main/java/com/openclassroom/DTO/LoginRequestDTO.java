package com.openclassroom.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequestDTO {
    @JsonProperty("login")
    private String email; // This will generate getEmail() and setEmail()
    private String password; // This will generate getPassword() and setPassword()
}