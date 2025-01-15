package com.openclassroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassroom.Entity.DBMessage;

// ----------------------------------------------------------------------------------------
// Basic CRUD operations are automatically provided by JpaRepository
// ----------------------------------------------------------------------------------------

@Repository
public interface MessageRepository extends JpaRepository<DBMessage, Integer> {
}