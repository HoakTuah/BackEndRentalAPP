package com.openclassroom.DTO;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class RentalDTO {
    private Integer id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private Integer owner_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public RentalDTO(Integer id, String name, BigDecimal surface, BigDecimal price,
            String picture, String description, Integer owner_id,
            LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // ----------------------------------------------------------------------------------------
    // Getters => Return the value of the property
    // ----------------------------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    // ----------------------------------------------------------------------------------------
    // Setters => Set the value of the property
    // ----------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}