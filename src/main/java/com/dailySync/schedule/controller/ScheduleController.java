package com.dailySync.schedule.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/schedule")
public class ScheduleController {
    final private ScheduleService scheduleService;

    @GetMapping("item/{userId}")
    public ResponseEntity<?> getschedule(@PathVariable Long userId) {
        return ResponseEntity.ok(scheduleService.AllSchedules(userId));
    }

    @PostMapping ("insert")
    public ResponseEntity<?> plusSchedule(ScheduleReqDto scheduleReqDto) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 4L;
        return ApiResponse.success(scheduleService.insertSchedule(userId, scheduleReqDto));
    }

    @PatchMapping ("update/{id}")
    public ResponseEntity<?> fixedSchedule(@PathVariable Long id, @RequestBody ScheduleReqDto reqDto) {
        return ApiResponse.success(scheduleService.updateSchedule(id, reqDto));
    }
    //제목만 검색하여 해당항목들 모두 불러오기
    @GetMapping ("title/{userId}/{title}")
    public ResponseEntity<?> findByTitleAboutAll(@PathVariable Long userId, @RequestParam String title) {
        return ResponseEntity.ok(scheduleService.searchByTitle(title));
    }

    //일정 삭제하기
    @DeleteMapping ("delete/{id}")
    public ResponseEntity<?> deleteByIdSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }

    //특정 날짜의 일정을 반환

}

