package com.dailySync.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReqDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String description;
}