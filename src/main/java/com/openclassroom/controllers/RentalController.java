package com.openclassroom.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.openclassroom.services.RentalService;
import com.openclassroom.exceptions.RentalNotFoundException;
import com.openclassroom.DTO.RentalDTO;
import com.openclassroom.DTO.RentalRequestDTO;
import com.openclassroom.Entity.DBRental;
import com.openclassroom.repository.RentalRepository;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

// ----------------------------------------------------------------------------------------
// REST Controller for managing rental operations
// Provides endpoints for CRUD operations on rentals
// ----------------------------------------------------------------------------------------

@RestController
@RequestMapping("/api/rentals")
@Validated
@Tag(name = "Rentals", description = "API pour la gestion des locations")
public class RentalController {

        // ----------------------------------------------------------------------------------------
        // Dependencies
        // ----------------------------------------------------------------------------------------

        private final RentalService rentalService;
        private final RentalRepository rentalRepository;

        public RentalController(RentalService rentalService, RentalRepository rentalRepository) {
                this.rentalService = rentalService;
                this.rentalRepository = rentalRepository;
        }

        // ----------------------------------------------------------------------------------------
        // Create Rental Endpoint
        // ----------------------------------------------------------------------------------------

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Créer une nouvelle location", description = "Permet de créer une nouvelle annonce de location", security = {
                        @SecurityRequirement(name = "Bearer Authentication") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Location créée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "message": "Rental created !"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                                "name": "Name is required",
                                                "surface": "Surface must be positive",
                                                "price": "Price must be positive",
                                                "description": "Description is required"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                                        "status": 401,
                                                        "message": "Invalid or expired token",
                                                        "error": "Authentication failed"
                                        }
                                        """)))

        })
        public ResponseEntity<Map<String, String>> createRental(
                        @ModelAttribute @Valid RentalRequestDTO request) { // Changed to @ModelAttribute

                try {
                        DBRental rental = rentalService.createRental(request); // Removed image parameter

                        Map<String, String> response = new HashMap<>();
                        response.put("message", "Rental created !");
                        if (rental.getPicture() != null) {
                                response.put("imageUrl", rental.getPicture());
                        }

                        return ResponseEntity.ok(response);

                } catch (Exception e) {
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Failed to create rental: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(errorResponse);
                }
        }

        // ----------------------------------------------------------------------------------------
        // Get All Rentals Endpoint
        // ----------------------------------------------------------------------------------------

        @GetMapping
        @Operation(summary = "Récupère toutes les locations", description = "Permet d'obtenir la liste de toutes les locations disponibles", security = {
                        @SecurityRequirement(name = "Bearer Authentication") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        [
                                            {
                                                "id": 1,
                                                "name": "Appartement Paris",
                                                "surface": 45,
                                                "price": 1200,
                                                "picture": "http://example.com/image.jpg",
                                                "description": "Bel appartement lumineux",
                                                "ownerId": 1,
                                                "createdAt": "2024-03-15T10:30:00",
                                                "updatedAt": "2024-03-15T10:30:00"
                                            },
                                            {
                                                "id": 2,
                                                "name": "Studio Lyon",
                                                "surface": 30,
                                                "price": 800,
                                                "picture": "http://example.com/image2.jpg",
                                                "description": "Studio centre-ville",
                                                "ownerId": 2,
                                                "createdAt": "2024-03-15T11:00:00",
                                                "updatedAt": "2024-03-15T11:00:00"
                                            }
                                        ]
                                        """))),
                        @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                                        {
                                                                        "status": 401,
                                                                        "message": "Invalid or expired token",
                                                                        "error": "Authentication failed"
                                                        }
                                        """)))
        })
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

        // ----------------------------------------------------------------------------------------
        // Get Single Rental Endpoint based on ID
        // ----------------------------------------------------------------------------------------

        @GetMapping("/{id}")
        @Operation(summary = "Récupérer une location par son ID", description = "Permet d'obtenir les détails d'une location spécifique", security = {
                        @SecurityRequirement(name = "Bearer Authentication") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Location trouvée", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "id": 1,
                                            "name": "Appartement Paris",
                                            "surface": 45,
                                            "price": 1200,
                                            "picture": "http://example.com/image.jpg",
                                            "description": "Bel appartement lumineux",
                                            "ownerId": 1,
                                            "createdAt": "2024-03-15T10:30:00",
                                            "updatedAt": "2024-03-15T10:30:00"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "message": "Rental not found with id: 1",
                                            "error": "Not Found",
                                            "status": 404
                                        }
                                        """))),
                        @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                                "status": 401,
                                                "message": "Invalid or expired token",
                                                "error": "Authentication failed"
                                        }
                                        """)))
        })
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

        // ----------------------------------------------------------------------------------------
        // Update Rental Endpoint
        // ----------------------------------------------------------------------------------------

        @PutMapping("/{id}")
        @Operation(summary = "Mettre à jour une location", description = "Permet de modifier les informations d'une location existante", security = {
                        @SecurityRequirement(name = "Bearer Authentication") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "message": "Rental updated!"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "name": "Le nom ne peut pas être vide",
                                            "surface": "La surface doit être supérieure à 0",
                                            "price": "Le prix doit être supérieur à 0",
                                            "description": "La description ne peut pas être vide"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "status": 404,
                                            "message": "Rental not found with id: 1",
                                            "error": " User Not Found"

                                        }
                                        """))),
                        @ApiResponse(responseCode = "403", description = "Accès non autorisé", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                            "status": 403,
                                            "message": "You are not authorized to modify this rental",
                                            "error": "Access denied"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                                        {
                                                "status": 401,
                                                "message": "Invalid or expired token",
                                                "error": "Authentication failed"
                                        }
                                        """)))
        })
        public ResponseEntity<Map<String, String>> updateRental(
                        @PathVariable Integer id,
                        @ModelAttribute @Valid RentalRequestDTO request) { // Changed to @ModelAttribute

                rentalService.updateRental(id, request);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Rental updated !");

                return ResponseEntity.ok(response);
        }
}