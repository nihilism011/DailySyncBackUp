package com.dailySync.todo.dto;

import com.dailySync.meal.dto.MealDayCntResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCountResponseDto {

    private LocalDate date;  // 날짜
    private Long CNT;  // 총 개수
    private Long checkedCnt;  // 체크된 개수
    private double completionRate;  // 완료율

}


