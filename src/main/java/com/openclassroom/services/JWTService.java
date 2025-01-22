package com.openclassroom.services;

import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JWTService {

    // ----------------------------------------------------------------------------------------
    // Dependencies
    // ----------------------------------------------------------------------------------------

    private final JwtEncoder jwtEncoder;
    private final long jwtExpiration;

    // Encoder for creating JWT tokens

    public JWTService(JwtEncoder jwtEncoder, @Value("${jwt.expiration}") long jwtExpiration) {
        this.jwtEncoder = jwtEncoder;
        this.jwtExpiration = jwtExpiration;
    }

    // ----------------------------------------------------------------------------------------
    // Generates a JWT token for an authenticated user
    // ----------------------------------------------------------------------------------------

    public String generateToken(Authentication authentication) {

        // Get info from Usermail and create a JWT token

        Instant now = Instant.now();
        String email = authentication.getName();

        // Build JWT claims set with standard and custom claims
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtExpiration)) // créer la variable pour la durée de l'expiration dans le

                .claim("email", email)
                .build();

        // Token Generation
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }
}