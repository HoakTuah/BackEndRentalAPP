package com.openclassroom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j

public class CloudinaryService {

    // ----------------------------------------------------------------------------------------
    // Dependencies
    // ----------------------------------------------------------------------------------------

    @Autowired
    private Cloudinary cloudinary;

    // ----------------------------------------------------------------------------------------
    // Cloudinary client for interacting with Cloudinary's API
    // ----------------------------------------------------------------------------------------

    public String uploadImage(MultipartFile file) {
        try {

            // ----------------------------------------------------------------------------------------
            // Upload Configuration
            // ----------------------------------------------------------------------------------------

            // Configure upload parameters
            Map<String, Object> uploadConfig = new HashMap<>();
            uploadConfig.put("resource_type", "auto");

            // ----------------------------------------------------------------------------------------
            // File Upload
            // ----------------------------------------------------------------------------------------

            // Upload file to Cloudinary and get result
            @SuppressWarnings("unchecked") // Add this annotation to suppress the warning
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader()
                    .upload(file.getBytes(), uploadConfig);

            return (String) uploadResult.get("url");

        } catch (IOException e) {
            log.error("Error uploading file to Cloudinary", e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}