package com.openclassroom.services;

import com.openclassroom.DTO.MessageDTO;
import com.openclassroom.Entity.DBMessage;
import com.openclassroom.Entity.DBUser;
import com.openclassroom.exceptions.RentalNotFoundException;
import com.openclassroom.Entity.DBRental;
import com.openclassroom.repository.MessageRepository;
import com.openclassroom.repository.UserRepository;
import com.openclassroom.repository.RentalRepository;
import com.openclassroom.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MessageService {

        // ----------------------------------------------------------------------------------------
        // Dependencies
        // ----------------------------------------------------------------------------------------

        @Autowired
        private MessageRepository messageRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RentalRepository rentalRepository;

        // ----------------------------------------------------------------------------------------
        // Retrieves all messages in the system and converts them to DTOs
        // ----------------------------------------------------------------------------------------

        public List<MessageDTO> getAllMessages() {

                // Fetch all messages from the database
                List<DBMessage> messages = messageRepository.findAll();

                // Convert each message entity to DTO and collect into a list
                return messages.stream()
                                .map(message -> new MessageDTO(
                                                message.getMessage(),
                                                message.getUser().getId(),
                                                message.getRental().getId(),
                                                message.getCreatedAt(),
                                                message.getUpdatedAt()))
                                .collect(Collectors.toList());
        }

        // ----------------------------------------------------------------------------------------
        // Creates a new message in the system
        // ----------------------------------------------------------------------------------------

        public MessageDTO createMessage(MessageDTO messageDTO) {

                // Find the user who is sending the message
                DBUser user = userRepository.findById(messageDTO.getUser_id())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "User not found with id: " + messageDTO.getUser_id()));

                // Find the rental the message is about
                DBRental rental = rentalRepository.findById(messageDTO.getRental_id())
                                .orElseThrow(
                                                () -> new RentalNotFoundException("Rental not found with id: "
                                                                + messageDTO.getRental_id()));

                // Message Creation
                DBMessage message = new DBMessage();
                message.setMessage(messageDTO.getMessage());
                message.setUser(user);
                message.setRental(rental);
                message.setCreatedAt(LocalDateTime.now());
                message.setUpdatedAt(LocalDateTime.now());

                DBMessage savedMessage = messageRepository.save(message);

                // DTO Conversion
                return new MessageDTO(
                                savedMessage.getMessage(),
                                savedMessage.getUser().getId(),
                                savedMessage.getRental().getId(),
                                savedMessage.getCreatedAt(),
                                savedMessage.getUpdatedAt());

        }
}