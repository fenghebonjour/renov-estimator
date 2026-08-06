package com.renovSolution.renov.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String LEAKED_SECRET = "RenovEstimatorSuperSecretKeyForJWTTokenGeneration2024!!LongEnough";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @PostConstruct
    private void validateSecret() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("jwt.secret must not be blank.");
        }
        if (secret.equals(LEAKED_SECRET)) {
            throw new IllegalStateException(
                    "jwt.secret is set to a value that was previously committed to git history and is compromised. Set JWT_SECRET to a new value.");
        }
    }

    private Key signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Date expiry = Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return username.equals(userDetails.getUsername()) && !expiry.before(new Date());
    }
}
