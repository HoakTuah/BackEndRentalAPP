package com.openclassroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----------------------------------------------------------------------------------------
    // Handle UserAlreadyExistsException
    // ----------------------------------------------------------------------------------------

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("message", e.getMessage());
        response.put("error", "Registration failed");

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // ----------------------------------------------------------------------------------------
    // Handle AuthenticationException
    // ----------------------------------------------------------------------------------------

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationFailedException(AuthenticationFailedException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("message", e.getMessage());
        response.put("error", "Authentication failed");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // ----------------------------------------------------------------------------------------
    // Handle RentalNotFoundException
    // ----------------------------------------------------------------------------------------

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRentalNotFoundException(RentalNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        response.put("error", "Rental not found");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // ----------------------------------------------------------------------------------------
    // Handle UnauthorizedRentalAccessException
    // ----------------------------------------------------------------------------------------

    @ExceptionHandler(UnauthorizedRentalAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedRentalAccessException(
            UnauthorizedRentalAccessException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("message", e.getMessage());
        response.put("error", "Access denied");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // ----------------------------------------------------------------------------------------
    // Handle UserNotFoundException
    // ----------------------------------------------------------------------------------------

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        response.put("error", "User not found");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}