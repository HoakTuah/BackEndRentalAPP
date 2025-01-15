package com.openclassroom.controllers;

import java.util.stream.Collectors;

import com.openclassroom.exceptions.RentalNotFoundException;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.openclassroom.services.RentalService;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.openclassroom.DTO.RentalDTO;
import com.openclassroom.DTO.RentalRequestDTO;
import java.util.List;
import com.openclassroom.Entity.DBRental;
import com.openclassroom.repository.RentalRepository;

import jakarta.validation.Valid;

// import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Messages", description = "Message management APIs")
public class RentalController {

        private final RentalService rentalService;
        private final RentalRepository rentalRepository;

        public RentalController(RentalService rentalService, RentalRepository rentalRepository) {
                this.rentalService = rentalService;
                this.rentalRepository = rentalRepository;
        }

        @PostMapping
        public ResponseEntity<String> createRental(@Valid @RequestBody RentalRequestDTO request) {
                rentalService.createRental(request);
                return ResponseEntity.ok("Rental created!");

        }

        @GetMapping
        public ResponseEntity<List<RentalDTO>> getAllRentals() {
                List<DBRental> rentals = rentalRepository.findAll();
                List<RentalDTO> rentalDTOs = rentals.stream()
                                .map(rental -> new RentalDTO(
                                                rental.getId(),
                                                rental.getName(),
                                                rental.getSurface(),
                                                rental.getPrice(),
                                                rental.getPicture(),
                                                rental.getDescription(),
                                                rental.getOwner().getId(),
                                                rental.getCreatedAt(),
                                                rental.getUpdatedAt()))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(rentalDTOs);
        }

        @GetMapping("/{id}")
        public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
                DBRental rental = rentalRepository.findById(id)
                                .orElseThrow(() -> new RentalNotFoundException("Rental not found with id: " + id));

                RentalDTO rentalDTO = new RentalDTO(
                                rental.getId(),
                                rental.getName(),
                                rental.getSurface(),
                                rental.getPrice(),
                                rental.getPicture(),
                                rental.getDescription(),
                                rental.getOwner().getId(),
                                rental.getCreatedAt(),
                                rental.getUpdatedAt());

                return ResponseEntity.ok(rentalDTO);
        }

        @PutMapping("/{id}")
        public ResponseEntity<String> updateRental(@PathVariable Integer id,
                        @Valid @RequestBody RentalRequestDTO request) {
                rentalService.updateRental(id, request);
                return ResponseEntity.ok("Rental updated!");
        }
}