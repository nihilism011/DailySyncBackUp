package com.dailySync.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDayCntResDto {
    private LocalDate date;
    private Integer week;
    private Long CNT;
}
