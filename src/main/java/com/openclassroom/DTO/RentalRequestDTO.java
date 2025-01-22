package com.openclassroom.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Surface is required")
    @Positive(message = "Surface must be positive")
    private BigDecimal surface;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private MultipartFile picture;

    @NotBlank(message = "Description is required")
    private String description;
}