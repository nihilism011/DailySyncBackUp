package com.dailySync.meal.controller;

import com.dailySync.meal.dto.MealReqDto;
import com.dailySync.meal.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("meal")
    public ResponseEntity<?> getMeal(@RequestBody MealReqDto mealReqDto) {
        return ResponseEntity.ok(mealService.getMeal(mealReqDto.getUserId()));
    }
}
