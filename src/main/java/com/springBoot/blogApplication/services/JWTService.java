package com.springBoot.blogApplication.services;

import com.springBoot.blogApplication.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private final String jtwtSecret = "cead5630592d638af9d792a3ad0c1fe353a78d7a9e80da294bab0e629aa9a090";

    public String generateToken(User user) {
        String token = Jwts.builder()
                .subject(user.getUsername()).
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).
                signWith(getSignInKey()).
                compact();
        return null;
    }

    public boolean isValidToken(String token, User user) {
        String username = extractUserName(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().
                verifyWith(getSignInKey()).
                build().
                parseSignedClaims(token).
                getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] secretBytes = jtwtSecret.getBytes();
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
