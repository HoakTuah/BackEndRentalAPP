package com.openclassroom.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}