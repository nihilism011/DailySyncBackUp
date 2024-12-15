package com.dailySync.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Long principal; // 사용자 정보
    private final String token;   // JWT 토큰

    public JwtAuthenticationToken(Long principal, String token) {
        super(Collections.emptyList());
        this.principal = principal;
        this.token = token;
        setAuthenticated(true); // 인증 상태 설정
    }

    @Override
    public Object getCredentials() {
        return token; // 토큰 반환
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자 정보 반환
    }
}