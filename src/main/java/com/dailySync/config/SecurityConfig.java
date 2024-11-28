package com.dailySync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/").permitAll()
                                //                                .requestMatchers("/student/**").hasRole("STUDENT")
                                //                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )

                .formLogin(formLoginConfig ->
                        formLoginConfig
                                .loginPage("/login")  // 커스텀 로그인 페이지 경로
                                .permitAll()  // 누구나 접근 가능
                )
                .logout(logoutConfig ->
                        logoutConfig
                                .logoutUrl("/logout")  // 커스텀 로그아웃 경로
                                .logoutSuccessUrl("/login?logout")  // 로그아웃 후 리디렉션할 URL
                                .permitAll()  // 누구나 로그아웃 경로에 접근 가능
                );
        return http.build();
    }

}

