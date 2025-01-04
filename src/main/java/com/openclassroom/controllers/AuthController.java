package com.openclassroom.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.openclassroom.Entity.DBUser;
import com.openclassroom.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository dBUserRepository;

    @GetMapping("/me")
    public ResponseEntity<String> testMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;

            // Get user from database using email
            DBUser user = dBUserRepository.findByEmail(jwtAuth.getToken().getClaim("email"));

            if (user != null) {
                return ResponseEntity.ok(
                        "ID: " + user.getId() +
                                ", Name: " + user.getUserName() +
                                ", Email: " + user.getUserMail() +
                                ", Created: " + user.getCreatedAt() +
                                ", Updated: " + user.getUpdatedAt());
            }
        }

        return ResponseEntity.ok("Unable to retrieve user details");
    }
}