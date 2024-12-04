package com.dailySync.meal.service;

import com.dailySync.meal.dto.MealListResDto;
import com.dailySync.meal.entities.Meal;
import com.dailySync.meal.repository.MealRepository;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public MealListResDto getAllUserMealList(Integer year, Integer month) {
        List<Meal> mealList = mealRepository.findMealsByUserIdADNYearAndMonth(7L, year, month);
        return new MealListResDto(mealList.stream().map(Meal::toRes).toList());
    }

    public MealListResDto getRecommandList() {
        List<Meal> recommandList = mealRepository.findByUserId(1L);
        return new MealListResDto(recommandList.stream().map(Meal::toRecom).toList());
    }

    public MealListResDto getFavoriteList(Long id) {
        List<Meal> favoriteList = mealRepository.findByUserIdAndIsFavorite(id, true);
        return new MealListResDto(favoriteList.stream().map(Meal::toRecom).toList());
    }

    public String insertMealList(List<Meal> meals) {
        User userId = userRepository.findById(7L).orElseThrow();
        try {
            for (Meal meal : meals) {
                mealRepository.save(Meal.ofSaveReq(userId, meal));
            }
            return "성공";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
