package com.dailySync.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    @PostConstruct
    public void logStartup() {

        System.out.println("서버 실행 완료!");
        System.out.println("SWAGGER API 명세 주소");
        System.out.println("http://localhost:8080/swagger-ui/index.html");
    }
}