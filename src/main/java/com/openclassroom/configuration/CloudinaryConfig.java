package com.openclassroom.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

// ----------------------------------------------------------------------------------------
// Configuration for Cloudinary integration
// ----------------------------------------------------------------------------------------

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    // ----------------------------------------------------------------------------------------
    // Creates and configures the Cloudinary instance
    // ----------------------------------------------------------------------------------------

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName); // Cloud name
        config.put("api_key", apiKey); // API key
        config.put("api_secret", apiSecret); // API secret
        return new Cloudinary(config); // Create and return Cloudinary instance
    }
}