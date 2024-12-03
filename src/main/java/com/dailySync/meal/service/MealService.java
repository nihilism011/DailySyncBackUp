package com.dailySync.meal.service;

import com.dailySync.meal.dto.MealListResDto;
import com.dailySync.meal.dto.MealReqDto;
import com.dailySync.meal.entities.Meal;
import com.dailySync.meal.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;

    public MealListResDto getAllUserMealList(Integer month) {
        List<Meal> mealList = mealRepository.findMealsByUserIdAndMonth(7L, month);
        return new MealListResDto(mealList.stream().map(Meal::toRes).toList());
    }

    public String insertMealList(List<Meal> meals) {
        try {
            for(Meal meal: meals) {
                //MealReqDto saveMeal = new MealReqDto();
                //mealRepository.save(meal);
            }
            return "성공";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
