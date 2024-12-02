package com.dailySync.meal.service;

import com.dailySync.meal.entities.Meal;
import com.dailySync.meal.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;

    public List<Meal> getMeal(Long id) {
        return mealRepository.findByUserId(id);
    }
}
