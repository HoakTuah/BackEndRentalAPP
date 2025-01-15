package com.openclassroom.controllers;

import com.openclassroom.DTO.MessageDTO;
import com.openclassroom.services.MessageService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<String> createMessage(@Valid @RequestBody MessageDTO messageDTO) {
        messageService.createMessage(messageDTO);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        List<MessageDTO> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
}