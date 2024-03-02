package com.springBoot.blogApplication.services;

import com.springBoot.blogApplication.entity.User;
import com.springBoot.blogApplication.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


//
//@Service
//public class JWTService {
//
//    private final TokenRepository tokenRepository;
//
//    public JWTService(TokenRepository tokenRepository) {
//        this.tokenRepository = tokenRepository;
//    }
//
//    public String generateToken(User user) {
//        return Jwts.builder()
//                .subject(user.getUsername()).
//                issuedAt(new Date(System.currentTimeMillis())).
//                expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).
//                signWith(getSignInKey()).
//                compact();
//    }
//
//
//
//    public boolean isValid(String token, UserDetails user) {
//        String username = extractUserName(token);
//
//        boolean validToken = tokenRepository
//                .findByToken(token)
//                .map(t -> !t.isLoggedOut())
//                .orElse(false);
//        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
//    }
//    public boolean isTokenExpired(String token) {
//        return extractClaims(token).getExpiration().before(new Date());
//    }
//
//    public String extractUserName(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
//        Claims claims = extractClaims(token);
//        System.out.println("Claims: " + claims);
//        return claimsTFunction.apply(claims);
//    }
//
//    private Claims extractClaims(String token) {
//        try {
//            return Jwts.parser()
//                    .verifyWith(getSignInKey())
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//        } catch (Exception e) {
//            System.out.println("Error extracting claims from token");
//            throw new RuntimeException("Error extracting claims from token", e);
//        }
//    }
//
//
//    private SecretKey getSignInKey() {
//        String jwtSecret = "cead5630592d638af9d792a3ad0c1fe353a78d7a9e80da294bab0e629aa9a090";
//        byte[] secretBytes = jwtSecret.getBytes();
//        return Keys.hmacShaKeyFor(secretBytes);
//    }
//}
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

    private final TokenRepository tokenRepository;

    public JWTService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isValid(String token, UserDetails user) {
        if (token == null || token.isEmpty()) {
            System.out.println("Invalid token: null or empty");
            return false;
        }
        String username = extractUserName(token);
        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("Invalid token: null or empty");
            return false;
        }

        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractClaims(token);
        System.out.println("Claims: {}"+ claims);
        return claimsTFunction.apply(claims);
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            System.out.println("Error extracting claims from token"+ e);
            throw new RuntimeException("Error extracting claims from token", e);
        }
    }

    private SecretKey getSignInKey() {
        String jwtSecret = "cead5630592d638af9d792a3ad0c1fe353a78d7a9e80da294bab0e629aa9a090";
        byte[] secretBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
