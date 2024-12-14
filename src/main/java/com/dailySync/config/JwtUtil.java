package com.dailySync.config;

import com.dailySync.user.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    public JwtUtil(@Value ("${jwt.secretKey}") String secretKey){
        byte[] secretKeyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String generateToken(User user) {
        long expirationTime = 1000 * 60 * 60 * 24 * 7;

        return Jwts.builder()
                .signWith(key)
                .subject(user.getUserName())
                .claim("userId",user.getId())
                .issuedAt(new Date()) // 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .compact(); // JWT 토큰 생성
    }
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("JWT 검증 실패", e);
        }
    }
    public Long extractUserId(String token){
        return extractClaims(token).get("userId",Long.class);
    }
    public String extractUserName(String token){
        return extractClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
