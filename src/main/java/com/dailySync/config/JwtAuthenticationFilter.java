package com.dailySync.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final String jwtPrefix = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if(token != null && jwtUtil.validateToken(token)){
            Long userId = jwtUtil.extractUserId(token);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith(jwtPrefix)){
            return header.substring(jwtPrefix.length());
        }
        return null;
    }
}
