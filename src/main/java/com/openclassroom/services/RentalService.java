package com.openclassroom.services;

import org.springframework.stereotype.Service;
import com.openclassroom.Entity.DBRental;
import com.openclassroom.repository.RentalRepository;
import com.openclassroom.DTO.RentalRequest;
import java.time.LocalDateTime;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public DBRental createRental(RentalRequest request, Integer ownerId) {
        DBRental rental = new DBRental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rental.setOwnerId(ownerId);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }
}