package com.dailySync.schedule.controller;

import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    final private ScheduleService scheduleService;

    @GetMapping("schedule")
    public ResponseEntity<?> getSchedule(@RequestBody ScheduleReqDto scheduleReqDto){

        return ResponseEntity.ok(scheduleService.getSchedule(scheduleReqDto.getUserId()));

    }
    





}
