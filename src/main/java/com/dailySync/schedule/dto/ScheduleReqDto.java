package com.dailySync.schedule.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReqDto {

    private Long userId;
    private String startTime;
    private String endTime;
    private String title;
    private String description;

}
