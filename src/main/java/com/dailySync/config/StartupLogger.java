package com.dailySync.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    @PostConstruct
    public void logStartup() {
        System.out.println("서버 실행 완료!");
    }
}