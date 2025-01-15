package com.openclassroom.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class DBRental {

    // ----------------------------------------------------------------------------------------
    // Maps columns names and properties of my rentals tables of my database
    // rentaluser
    // ----------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surface")
    private BigDecimal surface;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "picture")
    private String picture;

    @Column(name = "description", length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private DBUser owner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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

    public DBUser getOwner() {
        return owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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

    public void setOwner(DBUser owner) {
        this.owner = owner;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}