package com.openclassroom.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "users")
public class DBUser {

	// ----------------------------------------------------------------------------------------
	// Maps columns names and properties of my users tables of my database
	// rentaluser
	// ----------------------------------------------------------------------------------------

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
	private java.time.LocalDateTime created_at;

	@Column(name = "updated_at")
	private java.time.LocalDateTime updated_at;

	// ---------------------------------------------------------------------
	// Getters => Return the value of the property
	// ---------------------------------------------------------------------

	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return name;
	}

	public String getUserMail() {
		return email;
	}

	public String getPassword() {
		return password;
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

	public void setUserName(String name) {
		this.name = name;
	}

	public void setUserMail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedAt(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public void setUpdatedAt(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
}