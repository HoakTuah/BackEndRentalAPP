package com.openclassroom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassroom.DTO.RegisterRequest;
import com.openclassroom.Entity.DBUser;
import com.openclassroom.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public DBUser registerUser(RegisterRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Créer nouvel utilisateur
        DBUser user = new DBUser();
        user.setUserMail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserName(request.getName());
        user.setCreatedAt(request.getCreated_at());
        user.setUpdatedAt(request.getUpdated_at());

        // Sauvegarder l'utilisateur pour obtenir l'ID généré
        DBUser savedUser = userRepository.save(user);

        // Log avec l'ID
        System.out.println("Created user - ID: " + savedUser.getId() +
                ", Name: " + savedUser.getUserName() +
                ", Email: " + savedUser.getUserMail());

        return userRepository.save(user);
    }
}