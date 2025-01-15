package com.openclassroom.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "messages")
public class DBMessage {

    // ----------------------------------------------------------------------------------------
    // Maps columns names and properties of my messages table of my database
    // rentaluser
    // ----------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private DBRental rental;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DBUser user;

    @Column(name = "message", length = 2000)
    private String message;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    // ---------------------------------------------------------------------
    // Getters => Return the value of the property
    // ---------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public DBRental getRental() {
        return rental;
    }

    public DBUser getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    // ---------------------------------------------------------------------
    // Setters => Set the value of the property
    // ---------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRental(DBRental rental) {
        this.rental = rental;
    }

    public void setUser(DBUser user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}