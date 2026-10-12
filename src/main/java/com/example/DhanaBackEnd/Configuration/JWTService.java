package com.example.DhanaBackEnd.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {
    private final String SecretKey = "mySecretKeyForHRMSApplication1234567890";
    private final SecretKey key = Keys.hmacShaKeyFor(SecretKey.getBytes());

    public String generateToken(String email, String name, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 40
                )).signWith(key).compact();
    }

    public Claims getClaims(String token) {
        String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(cleanToken)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).get("email", String.class);

    }

    public String getNameFromToken(String token) {
        return getClaims(token).get("name", String.class);
    }

    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean validatetoken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key)
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

