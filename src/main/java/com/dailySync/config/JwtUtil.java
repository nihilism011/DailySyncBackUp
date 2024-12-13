package com.dailySync.config;

import com.dailySync.user.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 비밀 키를 생성하는 메서드
    private final SecretKey secretkey = Jwts.SIG.HS256.key().build();

    public String generateToken(User user) {
        // 토큰 유효시간 (예: 24시간)
        long expirationTime = 1000 * 60 * 60 * 24;

        return Jwts.builder()
                .subject(user.getUserName()) // Payload에 사용자 정보 설정
                .claim("userId",user.getId())
                .issuedAt(new Date()) // 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .signWith(secretkey) // 서명 알고리즘 및 비밀키 설정
                .compact(); // JWT 토큰 생성
    }
    // JWT 토큰 검증 메서드
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()// JWT 파서 빌더 사용
                    .verifyWith(secretkey) // 비밀 키 설정
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            throw new RuntimeException("JWT 검증 실패", e); // 검증 실패 시 예외 처리
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
    public boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
