package com.openclassroom.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDTO {
    @NotBlank(message = "Message content cannot be empty")
    private String message;

    @NotNull(message = "User ID is required")
    private Integer user_id;

    @NotNull(message = "Rental ID is required")
    private Integer rental_id;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}