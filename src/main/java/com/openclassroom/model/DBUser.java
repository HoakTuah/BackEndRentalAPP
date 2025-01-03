package com.openclassroom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "USERS")
public class DBUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "email")
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
	// Constructor
	// ---------------------------------------------------------------------

	public DBUser() {
	}

	public DBUser(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	// ---------------------------------------------------------------------
	// Getters
	// ---------------------------------------------------------------------

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return email;
	}

	public void setUsername(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}