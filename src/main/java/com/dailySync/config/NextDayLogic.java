package com.dailySync.config;

import com.dailySync.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class NextDayLogic {
    private final AccountService accountService;
}
