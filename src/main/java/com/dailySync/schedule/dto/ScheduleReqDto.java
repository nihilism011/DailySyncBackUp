package com.dailySync.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReqDto {

    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String description;

}
