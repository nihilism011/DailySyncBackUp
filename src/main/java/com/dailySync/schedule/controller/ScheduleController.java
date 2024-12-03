package com.dailySync.schedule.controller;

import com.dailySync.account.dto.AccountListResDto;
import com.dailySync.common.ApiResponse;
import com.dailySync.constant.ResMessage;
//import com.dailySync.constant.ResStatus;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/schedule/userId/{id}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> getSchedule(@PathVariable("id") Long userId){

        return ApiResponse.success(scheduleService.getUser(6L));
    }

//    @GetMapping("/schedule/")
//    public ResponseEntity<>{
//
//
//    }





}





