package com.dailySync.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor //기본 생성자 생성
@AllArgsConstructor //모든 필드값을 매개변수로 받는 생성자를 생성
public class ScheduleResDto {

    private Long id;
    private String userId;
    private String startTime;
    private String endTime;
    private String title;
    private String description;

}
