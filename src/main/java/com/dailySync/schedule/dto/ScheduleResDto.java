package com.dailySync.schedule.dto;

import com.dailySync.schedule.entities.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor //기본 생성자 생성
@AllArgsConstructor //모든 필드값을 매개변수로 받는 생성자를 생성
public class ScheduleResDto {

    private String startTime;
    private String endTime;
    private String title;
    private String description;


    public static ScheduleResDto of(Schedule schedule) {
        ScheduleResDto scheduleResDto = new ScheduleResDto();

        scheduleResDto.setStartTime(schedule.getStartTime());
        scheduleResDto.setEndTime(schedule.getEndTime());
        scheduleResDto.setTitle(schedule.getTitle());
        scheduleResDto.setDescription(schedule.getDescription());

        return scheduleResDto;
    }

}
