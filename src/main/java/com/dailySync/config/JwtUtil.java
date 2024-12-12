package com.dailySync.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // 비밀 키를 생성하는 메서드
    SecretKey secretkey = Jwts.SIG.HS256.key().build();

    public String generateToken(String username) {
        // 토큰 유효시간 (예: 24시간)
        long expirationTime = 1000 * 60 * 60 * 24;

        return Jwts.builder()
                .subject(username) // Payload에 사용자 정보 설정
                .issuedAt(new Date()) // 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .signWith(secretkey) // 서명 알고리즘 및 비밀키 설정
                .compact(); // JWT 토큰 생성
    }
    // JWT 토큰 검증 메서드
    public Claims validateToken(String token) {
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

}
