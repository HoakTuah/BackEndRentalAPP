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
import org.springframework.validation.annotation.Validated;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Tag(name = "Authentication", description = "API d'authentification pour la gestion des utilisateurs")
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

	@PostMapping("/register")
	@Operation(summary = "Inscription d'un nouvel utilisateur", description = "Crée un nouveau compte utilisateur et renvoie un token JWT")

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inscription réussie", content = @Content(mediaType = "application/json", schema = @Schema(example = """
					{
					    "message": "Registration successful",
					    "token": "eyJhbGciOiJIUzI1NiIsInR5..."
					}
					"""))),

			@ApiResponse(responseCode = "400", description = "Données invalides", content = @Content(mediaType = "application/json", schema = @Schema(example = """
					{
					       "errors": {
					           "name": "Name cannot be empty",
					           "email": "Email cannot be empty",
					           "password": "Password cannot be empty"
					       }
					   }
					   """))),

			@ApiResponse(responseCode = "409", description = "L'email existe déjà", content = @Content(mediaType = "application/json", schema = @Schema(example = """
					{
					    "message": "User with email string already exists",
								"error": "Registration failed",
								"status": 409
					}
					"""))),
	})

	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
		System.out.println("Register endpoint hit with request: " + request);
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

	@PostMapping("/login")
	@Operation(summary = "Authentification utilisateur", description = "Authentifie un utilisateur avec son email et mot de passe et renvoie un token JWT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authentification réussie", content = @Content(mediaType = "application/json", schema = @Schema(example = """
					{
					    "message": "Login successful",
					    "token": "eyJhbGciOiJIUzI1NiIsInR5..."
					}
					"""))),
			@ApiResponse(responseCode = "401", description = "Email ou mot de passe invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
					{
					    "message": "Invalid email or password",
						"error": "Authentication failed",
						"status": 401
					}
					""")))
	})
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

	@Autowired
	private UserRepository dBUserRepository;

	@GetMapping("/me")
	@Operation(summary = "Obtenir les informations de l'utilisateur connecté", description = "Récupère les informations de l'utilisateur à partir du token JWT", security = {
			@SecurityRequirement(name = "Bearer Authentication") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Informations utilisateur récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
					{
						"status": 401,
					    "message": "Invalid or expired token",
						"error": "Authentication failed"
					}
					"""))),
	})
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