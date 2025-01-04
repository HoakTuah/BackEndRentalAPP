package com.openclassroom.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.DTO.LoginRequest;
import com.openclassroom.DTO.RegisterRequest;
import com.openclassroom.Entity.DBUser;
import com.openclassroom.services.JWTService;
import com.openclassroom.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Login and Registration endpoints")
public class LoginController {

	private final JWTService jwtService;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	public LoginController(JWTService jwtService, UserService userService,
			AuthenticationManager authenticationManager) {

		this.jwtService = jwtService;
		this.userService = userService;
		this.authenticationManager = authenticationManager;

	}

	@PostMapping("/email")
	@Operation(summary = "Login with credentials")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getLogin(),
							loginRequest.getPassword()));
			String token = jwtService.generateToken(authentication);

			return ResponseEntity.ok(Map.of("token : ", token));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid email or password");
		}
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		System.out.println("Received request at /api/auth/register");
		try {
			DBUser user = userService.registerUser(request);

			// Créer un objet Authentication
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					user.getUserMail(),
					null,
					Collections.emptyList());

			// Générer le token
			String token = jwtService.generateToken(authentication);

			return ResponseEntity.ok(Map.of("token : ", token));

		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}