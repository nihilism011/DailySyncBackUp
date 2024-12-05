package com.dailySync.schedule.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping ("/schedule/userId/{userId}/{year}/{month}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> getSchedule(
            @PathVariable ("userId") Long userId,
            @PathVariable ("year") int year,
            @PathVariable ("month") int month)
    {
        List<ScheduleResDto> scheduleResDtos = scheduleService.getUser(userId, year, month);
        return ApiResponse.success(scheduleResDtos);
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

    @PostMapping ("/schedule/add")
    public ResponseEntity<ApiResponse<Boolean>> addSchedule(@RequestBody ScheduleReqDto reqDto) throws Exception {
        return ApiResponse.success(scheduleService.addSchedule(6L, reqDto));
    }

    @PostMapping("/schedule/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSchedule(@RequestBody Long id) throws Exception {
        return ApiResponse.success(scheduleService.deleteSchedule(id));
    }

    @PatchMapping ("/schedule/update")
    public ResponseEntity<ApiResponse<Boolean>> updateSchedule(@RequestBody ScheduleReqDto reqDto) throws Exception {
      return ApiResponse.success( scheduleService.updateSchedule(reqDto));
    }


}





