package com.dailySync.meal.dto;

import com.dailySync.meal.entities.Meal;
import com.dailySync.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealReqDto {
    private Long userId;
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

    public MealReqDto ofSaveReq(Meal meal) {
        if(meal == null) {
            return new MealReqDto();
        }
        MealReqDto mealReqDto = new MealReqDto();
        mealReqDto.setUserId(meal.getUser().getId());
        mealReqDto.setFoodName(meal.getFoodName());
        mealReqDto.setCategory(meal.getCategory());
        mealReqDto.setDescription(meal.getDescription());
        mealReqDto.setIcon(meal.getIcon());
        mealReqDto.setDate(meal.getDate());
        mealReqDto.setKcalories(mealReqDto.getKcalories());
        mealReqDto.setSugar(mealReqDto.getSugar());
        mealReqDto.setSodium(mealReqDto.getSodium());
        mealReqDto.setProtein(mealReqDto.getProtein());
        mealReqDto.setFat(mealReqDto.getFat());
        mealReqDto.setCarbs(mealReqDto.getCarbs());
        mealReqDto.setFavorite(mealReqDto.isFavorite());
        return mealReqDto;
    }

}
