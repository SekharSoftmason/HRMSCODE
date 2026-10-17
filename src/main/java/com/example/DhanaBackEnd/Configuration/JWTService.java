package com.example.DhanaBackEnd.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                    "JWT secret must contain at least 32 bytes");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String email, String name, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 40
                )).signWith(getSigningKey()).compact();
    }

    public Claims getClaims(String token) {
        String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(cleanToken)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).get("email", String.class);

    }

    public String getNameFromToken(String token) {
        return getClaims(token).get("name", String.class);
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
        .get("role", String.class);
    }

    public boolean validatetoken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey())
                    .build().parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e ){
            return false;
        }
    }
        public boolean validateAuthorizationHeader (String authorizationHeader){
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return false;
            }
            String token = authorizationHeader.substring(7);
            return validatetoken(token);
        }

    }

