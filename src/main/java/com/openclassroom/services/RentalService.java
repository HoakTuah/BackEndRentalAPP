package com.openclassroom.services;

import com.openclassroom.exceptions.RentalNotFoundException;
import com.openclassroom.exceptions.UnauthorizedRentalAccessException;

// Spring security imports
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

// Entity and Repository imports
import com.openclassroom.Entity.DBRental;
import com.openclassroom.repository.RentalRepository;
import com.openclassroom.repository.UserRepository;
import com.openclassroom.DTO.RentalRequestDTO;
import com.openclassroom.Entity.DBUser;

import java.time.LocalDateTime;
// import java.util.List;

@Service
public class RentalService {

    // ----------------------------------------------------------------------------------------
    // Repository dependencies
    // ----------------------------------------------------------------------------------------
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    // ----------------------------------------------------------------------------------------
    // Constructor (are passed through the constructor )
    // ----------------------------------------------------------------------------------------
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    // ----------------------------------------------------------------------------------------
    // Update rental
    // ----------------------------------------------------------------------------------------

    public DBRental updateRental(Integer id, RentalRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        String userEmail = jwtAuth.getToken().getClaim("email");

        DBRental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found with id: " + id));

        // Check if the current user is the owner
        if (!rental.getOwner().getUserMail().equals(userEmail)) {
            throw new UnauthorizedRentalAccessException("You are not authorized to modify this rental");
        }

        // Update the rental properties
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());

        return rentalRepository.save(rental);
    }

    // ----------------------------------------------------------------------------------------
    // Create a rental
    // ----------------------------------------------------------------------------------------

    public DBRental createRental(RentalRequestDTO request) {

        // ----------------------------------------------------------------------------------------
        // Get the current authenticated user from Spring Security context
        // ----------------------------------------------------------------------------------------

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // ----------------------------------------------------------------------------------------
        // Extract the user's email from the JWT token
        // ----------------------------------------------------------------------------------------

        String userEmail = null;
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userEmail = jwt.getClaimAsString("email");
        }

        // ----------------------------------------------------------------------------------------
        // Find the user by email to get the user ID
        // ----------------------------------------------------------------------------------------

        DBUser user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UnauthorizedRentalAccessException("User not found");
        }

        // Create a new rental
        DBRental rental = new DBRental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rental.setOwner(user);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);

    }
}