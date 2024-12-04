package com.dailySync.meal.service;

import com.dailySync.meal.dto.MealListResDto;
import com.dailySync.meal.dto.MealReqDto;
import com.dailySync.meal.entities.Meal;
import com.dailySync.meal.repository.MealRepository;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public Boolean insertMealList(List<Meal> meals) {
        User userId = userRepository.findById(7L).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + "Kim"));

        for (Meal meal : meals) {
            if(meal.getIsFavorite()) {
                mealRepository.save(Meal.ofSaveReq(userId, meal, true));
                mealRepository.save(Meal.ofSaveReq(userId, meal, false));
            } else {
                mealRepository.save(Meal.ofSaveReq(userId, meal, false));
            }
        }
        return true;
    }

    public Boolean updateMeal(MealReqDto mealReqDto) {
        Meal meal = mealRepository.findById(mealReqDto.getId()).orElseThrow(() -> new EntityNotFoundException("Meal not found with id: " + mealReqDto.getId()));

        meal.update(mealReqDto);
        mealRepository.save(meal);
        return true;
    }

    public Boolean deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Meal not found with id: " + id));
        System.out.println(id);
        mealRepository.delete(meal);
        return true;
    }
}
