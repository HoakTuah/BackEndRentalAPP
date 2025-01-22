package com.openclassroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.Entity.DBRental;
import java.util.UUID;

// ----------------------------------------------------------------------------------------
// Basic CRUD operations are automatically provided by JpaRepository
// ----------------------------------------------------------------------------------------

@Repository
public interface ImageRepository extends JpaRepository<DBRental, UUID> {
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