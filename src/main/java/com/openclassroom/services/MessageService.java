package com.openclassroom.services;

import com.openclassroom.DTO.MessageDTO;
import com.openclassroom.Entity.DBMessage;
import com.openclassroom.Entity.DBUser;
import com.openclassroom.exceptions.RentalNotFoundException;
import com.openclassroom.Entity.DBRental;
import com.openclassroom.repository.MessageRepository;
import com.openclassroom.repository.UserRepository;
import com.openclassroom.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassroom.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MessageService {

        @Autowired
        private MessageRepository messageRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RentalRepository rentalRepository;

        public List<MessageDTO> getAllMessages() {
                List<DBMessage> messages = messageRepository.findAll();

                return messages.stream()
                                .map(message -> new MessageDTO(
                                                message.getMessage(),
                                                message.getUser().getId(),
                                                message.getRental().getId(),
                                                message.getCreatedAt(),
                                                message.getUpdatedAt()))
                                .collect(Collectors.toList());
        }

        public MessageDTO createMessage(MessageDTO messageDTO) {
                // Find the related entities
                DBUser user = userRepository.findById(messageDTO.getUserId())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "User not found with id: " + messageDTO.getUserId()));

                // Find the rental
                DBRental rental = rentalRepository.findById(messageDTO.getRentalId())
                                .orElseThrow(
                                                () -> new RentalNotFoundException("Rental not found with id: "
                                                                + messageDTO.getRentalId()));

                DBMessage message = new DBMessage();
                message.setMessage(messageDTO.getMessage());
                message.setUser(user);
                message.setRental(rental);
                message.setCreatedAt(LocalDateTime.now());
                message.setUpdatedAt(LocalDateTime.now());

                DBMessage savedMessage = messageRepository.save(message);

                // Convert back to DTO
                return new MessageDTO(
                                savedMessage.getMessage(),
                                savedMessage.getUser().getId(),
                                savedMessage.getRental().getId(),
                                savedMessage.getCreatedAt(),
                                savedMessage.getUpdatedAt());

        }
}