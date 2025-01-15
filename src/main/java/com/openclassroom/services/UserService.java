package com.openclassroom.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassroom.DTO.RegisterRequestDTO;
import com.openclassroom.Entity.DBUser;
import com.openclassroom.exceptions.UserAlreadyExistsException;
import com.openclassroom.repository.UserRepository;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ----------------------------------------------------------------------------------------
    // Register a new user
    // ----------------------------------------------------------------------------------------

    public DBUser registerUser(RegisterRequestDTO request) {

        // ----------------------------------------------------------------------------------------
        // Check if the email already exists
        // ----------------------------------------------------------------------------------------

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        // ----------------------------------------------------------------------------------------
        // Create a new user
        // ----------------------------------------------------------------------------------------

        DBUser user = new DBUser();
        user.setUserMail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setUserName(request.getName().trim());

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

}