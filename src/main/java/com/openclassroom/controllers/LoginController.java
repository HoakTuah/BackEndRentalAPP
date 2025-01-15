package com.openclassroom.controllers;

import com.openclassroom.exceptions.AuthenticationFailedException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.DTO.LoginRequestDTO;
import com.openclassroom.DTO.RegisterRequestDTO;
import com.openclassroom.DTO.UserDTO;
import com.openclassroom.Entity.DBUser;
import com.openclassroom.repository.UserRepository;
import com.openclassroom.services.JWTService;
import com.openclassroom.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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

	@PostMapping("/login")
	@Operation(summary = "Login with credentials")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getemail(),
							loginRequest.getPassword()));
			String token = jwtService.generateToken(authentication);

			Map<String, Object> response = new LinkedHashMap<>();
			response.put("message", "Login successful");
			response.put("token", token);

			return ResponseEntity.ok(response);

		} catch (AuthenticationException e) {
			throw new AuthenticationFailedException("Invalid email or password");
		}
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
		DBUser user = userService.registerUser(request);

		// Create Authentication object
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				user.getUserMail(),
				null,
				Collections.emptyList());

		// Generate token
		String token = jwtService.generateToken(authentication);

		// Use LinkedHashMap to maintain order
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("message", "Registration successful");
		response.put("token", token);
		return ResponseEntity.ok(response);
	}

	@Autowired
	private UserRepository dBUserRepository;

	@GetMapping("/me")
	public ResponseEntity<?> testMe() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
		String email = jwtAuth.getToken().getClaim("email");

		DBUser user = dBUserRepository.findByEmail(email);

		UserDTO userDTO = new UserDTO(
				user.getId(),
				user.getUserMail(),
				user.getUserName(),
				user.getCreatedAt(),
				user.getUpdatedAt());

		return ResponseEntity.ok(userDTO);

	}

}