package com.openclassroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassroom.Entity.DBUser;

public interface UserRepository extends JpaRepository<DBUser, Integer> {
	public DBUser findByEmail(String email);
}