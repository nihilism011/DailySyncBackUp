package com.dailySync.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealListResDto {
    private Map<LocalDate, List<MealResDto>> mealList;
}
