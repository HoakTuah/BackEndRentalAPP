package com.openclassroom.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.openclassroom.services.RentalService;
import com.openclassroom.DTO.RentalRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<?> createRental(
            @Valid @RequestBody RentalRequest request,
            Authentication authentication) {
        // Supposons que nous avons l'ID de l'utilisateur dans l'authentification
        Integer ownerId = 1; // À remplacer par l'ID réel de l'utilisateur authentifié

        return ResponseEntity.ok(rentalService.createRental(request, ownerId));
    }
}