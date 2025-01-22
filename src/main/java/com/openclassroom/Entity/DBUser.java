package com.openclassroom.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class DBUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Custom getters to maintain backward compatibility with existing code
	public String getUserName() {
		return name;
	}

	public String getUserMail() {
		return email;
	}

	// Custom setters to maintain backward compatibility with existing code
	public void setUserName(String name) {
		this.name = name;
	}

	public void setUserMail(String email) {
		this.email = email;
	}

}