package com.dailySync.meal.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.meal.dto.MealListResDto;
import com.dailySync.meal.dto.MealResDto;
import com.dailySync.meal.entities.Meal;
import com.dailySync.meal.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    /**로그인 한 유저가 월을 month 변수에 담아 넘겨주면 해당 월의 mealList 를 전달 */
    @GetMapping("/meal/mealList/{month}")
    public ResponseEntity<ApiResponse<MealListResDto>> getMealList(@PathVariable int month) {
        return ApiResponse.success(mealService.getAllUserMealList(month));
    }

    /** 식단 데이터 삽입시 데이터 적용 */
    @PostMapping("/meal")
    public ResponseEntity<ApiResponse<String>> postMealList(@RequestBody List<Meal> meals){
        return ApiResponse.success(mealService.insertMealList(meals));
    }
}
