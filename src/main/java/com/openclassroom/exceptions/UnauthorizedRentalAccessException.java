package com.openclassroom.exceptions;

public class UnauthorizedRentalAccessException extends RuntimeException {
    public UnauthorizedRentalAccessException(String message) {
        super(message);
    }
}