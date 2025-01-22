package com.openclassroom.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
    private Integer id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;

    @JsonProperty("owner_id")
    private Integer owner_id;

    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;
}