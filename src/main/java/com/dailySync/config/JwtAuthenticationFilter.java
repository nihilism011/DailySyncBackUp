package com.dailySync.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if(token != null && jwtUtil.validateToken(token)){
            Long userId = jwtUtil.extractUserId(token);
            SecurityContextHolder.getContext().setAuthentication(
                    new JwtAuthenticationToken(userId,token)
            );
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String jwtPrefix = "Bearer ";
        if(header!=null && header.startsWith(jwtPrefix)){
            return header.substring(jwtPrefix.length());
        }
        return null;
    }
}
