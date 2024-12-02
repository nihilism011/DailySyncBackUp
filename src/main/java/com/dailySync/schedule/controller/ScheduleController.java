package com.dailySync.schedule.controller;

import com.dailySync.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    final private ScheduleService scheduleService;

    @GetMapping("schedule/getSchedule")
    public ResponseEntity<?> getSchedule(){

        return ResponseEntity.ok(scheduleService.getSchedule());

    }
    





}
