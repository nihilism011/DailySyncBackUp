package com.dailySync.schedule.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping ("/schedule/userId/{id}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> getSchedule(@PathVariable ("id") Long userId) {
        return ApiResponse.success(scheduleService.getUser(6L));
    }

    @GetMapping ("/schedule/title/{title}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchSchedule(
            @PathVariable ("title") String title

    ) {
        return ApiResponse.success(scheduleService.getScheduleTitle(6L, title));
    }

    @GetMapping ("/schedule/date/{year}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchSchedule(
            @PathVariable ("year") int year

    ) {
        return ApiResponse.success(scheduleService.getScheduleYear(6L, year));
    }

    @GetMapping ("/schedule/date/{year}/{month}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchSchedule(
            @PathVariable ("year") int year,
            @PathVariable ("month") int month

    ) {
        return ApiResponse.success(scheduleService.getScheduleDate(6L, year, month));
    }

}





