package com.openclassroom.controllers;

import com.openclassroom.DTO.MessageDTO;
import com.openclassroom.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

// ----------------------------------------------------------------------------------------
// REST Controller for managing message operations
// Provides endpoints for creating messages
// ----------------------------------------------------------------------------------------

@RestController
@RequestMapping("/api/messages")
@Validated
@Tag(name = "Messages", description = "API de gestion des messages")
public class MessageController {

    // ----------------------------------------------------------------------------------------
    // Dependencies
    // ----------------------------------------------------------------------------------------

    @Autowired
    private MessageService messageService;

    // ----------------------------------------------------------------------------------------
    // Create Message Endpoint
    // ----------------------------------------------------------------------------------------

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Envoyer un message", description = "Permet d'envoyer un nouveau message pour une location", security = {
            @SecurityRequirement(name = "Bearer Authentication") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message envoyé avec succès", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                        "message": "Message send with success !"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                        "errors": {
                            "message": "Le message ne peut pas être vide"
                        }
                    }
                                """))),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                        "message": "Rental not found with id: 1 or user not found with id: 1",
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(example = """
            {
                "message": "test",
                "user_id": 1,
                "rental_id": 1
            }
            """)))
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody MessageDTO messageDTO) {
        messageService.createMessage(messageDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success !");
        return ResponseEntity.ok(response);
    }

    // @GetMapping
    // public ResponseEntity<List<MessageDTO>> getAllMessages() {
    // List<MessageDTO> messages = messageService.getAllMessages();
    // return ResponseEntity.ok(messages);
    // }
}