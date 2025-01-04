package com.openclassroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassroom.Entity.DBRental;

@Repository
public interface RentalRepository extends JpaRepository<DBRental, Integer> {

}