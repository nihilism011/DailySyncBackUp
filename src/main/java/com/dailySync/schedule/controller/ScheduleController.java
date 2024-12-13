package com.dailySync.schedule.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.entities.ScheduleDetail;
import com.dailySync.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    /** 로그인 한 유저가 연도 year 월 month 변수에 담아 넘겨주면 해당 월의 sheduleList 를 전달 */
    @Operation
            (
                    summary = "로그인한 유저가 선택한 달의 리스트 불러오기(초기 오늘의 월)",
                    description = "year는 연도, month는 월을 입력한다."
            )
    @GetMapping ("userId/{userId}/{year}/{month}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> getSchedule(
            @PathVariable ("userId") Long userId,
            @PathVariable ("year") int year,
            @PathVariable ("month") int month)
    {
        List<ScheduleResDto> scheduleResDtos = scheduleService.getUser(userId, year, month);
        return ApiResponse.success(scheduleResDtos);
    }

    /** 로그인 한 유저가 schedule의 id를 변수에 담아 넘겨주면 해당 sheduleList 를 전달 */
    @Operation
            (
                    summary = "로그인한 유저가 선택한 일정의 리스트 불러오기",
                    description = "id를 입력한다."
            )
    @GetMapping ("userId/id/{userId}/{id}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> getScheduleInfo(
            @PathVariable ("userId") Long userId,
            @PathVariable ("id") Long id)
    {
        List<ScheduleResDto> scheduleResDtos = scheduleService.getSchedule(userId,id);
        return ApiResponse.success(scheduleResDtos);
    }

    /** 제목을 검색해 유저의 일정리스트 찾기 */
    @Operation
            (
                    summary = "로그인한 유저가 검색한 제목으로 일정리스트 불러오기",
                    description = "title은 일정의 제목을 입력한다."
            )
    @GetMapping ("title/{title}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchSchedule(
            @PathVariable ("title") String title
    ) {
        return ApiResponse.success(scheduleService.getScheduleTitle(6L, title));
    }

    /** 연도별로 일정리스트 찾기 */
    @Operation
            (
                    summary = "로그인한 유저가 선택한 연도로 일정리스트 불러오기",
                    description = "year는 연도를 입력한다."
            )
    @GetMapping ("date/{year}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchSchedule(
            @PathVariable ("year") int year
    ) {
        return ApiResponse.success(scheduleService.getScheduleYear(6L, year));
    }

    /** 기간설정으로 일정리스트 찾기 */
    @GetMapping("date/range")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchScheduleInRange(
            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime
    ) {
        List<ScheduleResDto> schedules = scheduleService.getScheduleInRange(6L, startTime, endTime);
        return ApiResponse.success(schedules);
    }

    /** 연도와 달을 이용하여 일정리스트 찾기 */
    @Operation
            (
                    summary = "로그인한 유저가 선택한 연도의 달로 일정리스트 불러오기",
                    description = "year는 연도, month는 월을 입력한다."
            )
    @GetMapping ("date/{year}/{month}")
    public ResponseEntity<ApiResponse<List<ScheduleResDto>>> searchSchedule(
            @PathVariable ("year") int year,
            @PathVariable ("month") int month
    ) {
        return ApiResponse.success(scheduleService.getScheduleDate(6L, year, month));
    }

    /** 일정 데이터 추가 */
    @Operation
            (
                    summary = "일정 리스트(데이터) 삽입",
                    description = "해당 일자 데이터 삽입할때 List에 객체를 담아와서 데이터를 삽입한다."
            )
    @PostMapping ("add")
    public ResponseEntity<ApiResponse<Boolean>> addSchedule(@RequestBody ScheduleReqDto reqDto) throws Exception {
        return ApiResponse.success(scheduleService.addSchedule(6L, reqDto));
    }

    /** 일정 데이터 수정 */
    @Operation
            (
                    summary = "일정 데이터 수정",
                    description = "등록된 데이터를 수정한다."
            )
    @PatchMapping ("update/{id}")
    public ResponseEntity<?> fixedSchedule(@PathVariable Long id, @RequestBody ScheduleReqDto reqDto) {
        return ApiResponse.success(scheduleService.updateSchedule(id, reqDto));
    }

    /** 일정 데이터 삭제 */
    @Operation
            (
                    summary = "일정 목록 삭제",
                    description = "일정이 가지고있는 고유 id를 사용하여 삭제한다."
            )
    @DeleteMapping ("delete/{id}")
    public ResponseEntity<?> deleteByIdSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }




}