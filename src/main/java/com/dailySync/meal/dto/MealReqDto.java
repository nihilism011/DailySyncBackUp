package com.dailySync.meal.dto;

import com.dailySync.constant.MealCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealReqDto {
    private Long id;
    private Long userId;
    private String foodName;
    private MealCategory category;
    private String description;
    private String icon;
    private LocalDate date;
    private Integer kcalories;
    private Integer sugar;
    private Integer sodium;
    private Integer protein;
    private Integer fat;
    private Integer carbs;
    private Boolean isFavorite;
}
