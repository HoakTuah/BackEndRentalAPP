package com.openclassroom.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.openclassroom.exceptions.RentalNotFoundException;
import com.openclassroom.exceptions.UnauthorizedRentalAccessException;
import com.openclassroom.repository.RentalRepository;
import com.openclassroom.repository.UserRepository;
import com.openclassroom.DTO.RentalRequestDTO;
import com.openclassroom.Entity.DBRental;
import com.openclassroom.Entity.DBUser;

import java.time.LocalDateTime;

@Service
public class RentalService {

    // ----------------------------------------------------------------------------------------
    // Dependencies
    // ----------------------------------------------------------------------------------------

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    // ----------------------------------------------------------------------------------------
    // Constructor for dependency injection
    // ----------------------------------------------------------------------------------------

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository,
            CloudinaryService cloudinaryService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
    }

    // ----------------------------------------------------------------------------------------
    // Updates an existing rental property
    // ----------------------------------------------------------------------------------------

    public DBRental updateRental(Integer id, RentalRequestDTO request) {

        // Get current user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        String userEmail = jwtAuth.getToken().getClaim("email");

        // Find the rental or throw exception if not found
        DBRental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found with id: " + id));

        // Verify ownership
        if (!rental.getOwner().getUserMail().equals(userEmail)) {
            throw new UnauthorizedRentalAccessException("You are not authorized to modify this rental");
        }

        // Process new image if provided
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(request.getPicture());
            rental.setPicture(imageUrl);
        }

        // Update the rental properties
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());

        return rentalRepository.save(rental);
    }

    // ----------------------------------------------------------------------------------------
    // Create a new rental
    // ----------------------------------------------------------------------------------------

    public DBRental createRental(RentalRequestDTO request) {

        // Authentication

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the user's email from the JWT token

        String userEmail = null;
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userEmail = jwt.getClaimAsString("email");
        }

        // Verify user exists
        DBUser user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UnauthorizedRentalAccessException("User not found");
        }

        // Image Processing
        String imageUrl = null;
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(request.getPicture());
        }

        // Create a new rental
        DBRental rental = new DBRental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(imageUrl);
        rental.setDescription(request.getDescription());
        rental.setOwner(user);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);

    }
}