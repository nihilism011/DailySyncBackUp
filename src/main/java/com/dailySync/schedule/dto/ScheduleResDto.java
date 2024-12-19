package com.dailySync.schedule.dto;

import com.dailySync.schedule.entities.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResDto {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String description;

    public static ScheduleResDto of(Schedule schedule) {
        ScheduleResDto scheduleResDto = new ScheduleResDto();
        scheduleResDto.setId(schedule.getId());
        scheduleResDto.setStartTime(schedule.getStartTime());
        scheduleResDto.setEndTime(schedule.getEndTime());
        scheduleResDto.setTitle(schedule.getTitle());
        scheduleResDto.setDescription(schedule.getDescription());
        return scheduleResDto;
    }
}