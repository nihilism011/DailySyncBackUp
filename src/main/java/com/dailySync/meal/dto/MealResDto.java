package com.dailySync.meal.dto;

import com.dailySync.meal.entities.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealResDto {
    private String foodName;
    private String category;
    private String description;
    private String icon;
    private LocalDate date;
    private Integer kcalories;
    private Integer sugar;
    private Integer sodium;
    private Integer protein;
    private Integer fat;
    private Integer carbs;
    private boolean isFavorite;

    public static MealResDto of(Meal meal){
        MealResDto mealResDto = new MealResDto();
        mealResDto.setFoodName(meal.getFoodName());
        mealResDto.setCategory(meal.getCategory());
        mealResDto.setDescription(meal.getDescription());
        mealResDto.setIcon(meal.getIcon());
        mealResDto.setDate(meal.getDate());
        mealResDto.setKcalories(meal.getKcalories());
        mealResDto.setSugar(meal.getSugar());
        mealResDto.setSodium(meal.getSodium());
        mealResDto.setProtein(meal.getProtein());
        mealResDto.setFat(meal.getFat());
        mealResDto.setCarbs(meal.getCarbs());
        mealResDto.setFavorite(mealResDto.isFavorite());
        return mealResDto;
    }
}
