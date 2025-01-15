package com.openclassroom.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MessageDTO {
    @NotBlank(message = "Message content cannot be empty")
    private String message;

    @NotNull(message = "User ID is required")
    private Integer user_id;

    @NotNull(message = "Rental ID is required")
    private Integer rental_id;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public MessageDTO(String message, Integer user_id, Integer rental_id,
            LocalDateTime created_at, LocalDateTime updated_at) {
        this.message = message;
        this.user_id = user_id;
        this.rental_id = rental_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public Integer getRentalId() {
        return rental_id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setRentalId(Integer rental_id) {
        this.rental_id = rental_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}