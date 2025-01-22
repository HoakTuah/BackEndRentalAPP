package com.openclassroom.Entity;

import java.time.LocalDateTime;
import lombok.Data;
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
@Data // Generates getters, setters, toString, equals, hashCode, and required
      // constructors
public class DBMessage {

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
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}