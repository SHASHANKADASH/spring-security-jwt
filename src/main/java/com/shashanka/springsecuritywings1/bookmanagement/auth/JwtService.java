package com.shashanka.springsecuritywings1.bookmanagement.auth;

import com.shashanka.springsecuritywings1.bookmanagement.user.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "b51bc45f86629429a865546f52a325b32d2b5114c0c598c53f4906d2f9c239fe9084291d7b7192c229c8831af556f2dd19fbde5391629d394c342681af69673ab41a15e8315daca2b2edad2ac040fca028a2c678cf495b4be087722128f4b6605f2a9684cff066d02dedd1a45e46e87857b86c3b4bdb9dabb13764162fd06ee28294a5c9b1d2269d06dcb24c5f6bc04b00a78a134cb150c5babf9ba7a1c0daee9bfc7c00d5d5850cc858d26e77ce680dd99f168095cc003d3b17c5d0feab6c37198981cd47eaf3eb439796e68cb13644b494c656d811c93d5dc1e23696ae3ee8262950def3206c794655b4e1844a0d8b3e8e9f61a2437e2ef6c4f2af7ef18b70";
    
    
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(
            UserDetails userDetails,
            List<Role> roles
    ){
        return generateToken(Map.of("roles", roles), userDetails);
    }

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
