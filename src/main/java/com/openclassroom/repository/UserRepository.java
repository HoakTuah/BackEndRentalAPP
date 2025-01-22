package com.openclassroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassroom.Entity.DBUser;

// ----------------------------------------------------------------------------------------
// Basic CRUD operations are automatically provided by JpaRepository
// ----------------------------------------------------------------------------------------

public interface UserRepository extends JpaRepository<DBUser, Integer> {

	// ----------------------------------------------------------------------------------------
	// Custom Query Methods: Finds a user by their email address
	// ----------------------------------------------------------------------------------------

	public DBUser findByEmail(String email);

	// ----------------------------------------------------------------------------------------
	// Inherited JpaRepository Methods:
	// ----------------------------------------------------------------------------------------
	// - save(DBMessage entity) : Saves a message
	// - findById(Integer id) : Finds a message by ID
	// - findAll() : Retrieves all messages
	// - delete(DBMessage entity) : Deletes a message
	// - deleteById(Integer id) : Deletes a message by ID
	// - existsById(Integer id) : Checks if a message exists
	// - count() : Returns total number of messages
	// ----------------------------------------------------------------------------------------

}