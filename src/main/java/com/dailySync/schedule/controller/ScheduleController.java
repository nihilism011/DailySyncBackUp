package com.dailySync.schedule.controller;

import com.dailySync.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    @GetMapping ("schedule/getSchedule")
    public ResponseEntity<ApiResponse<Boolean>> getSchedule() {
        return ApiResponse.success(true);
    }
}