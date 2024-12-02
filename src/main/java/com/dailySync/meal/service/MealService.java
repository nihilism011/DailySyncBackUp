package com.dailySync.meal.service;

import com.dailySync.meal.dto.MealResDto;
import com.dailySync.meal.entities.Meal;
import com.dailySync.meal.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRopository;

    public List<Meal> getMeal(Long id) {
        List<Meal> mealList = mealRopository.findByUserId(id);
        return mealList;
    }
}
