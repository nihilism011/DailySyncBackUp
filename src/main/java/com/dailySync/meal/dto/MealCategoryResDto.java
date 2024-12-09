package com.dailySync.meal.dto;

import com.dailySync.constant.MealCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MealCategoryResDto {
    private String name;
    private String eName;

    public static MealCategoryResDto fromMealCategory(MealCategory category) {
        return new MealCategoryResDto(category.getName(), category.getEName());
    }
}
