package com.dailySync.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    public Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
