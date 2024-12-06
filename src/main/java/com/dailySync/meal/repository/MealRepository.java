package com.dailySync.meal.repository;

import com.dailySync.meal.dto.MealDayCntResDto;
import com.dailySync.meal.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    @Query ("SELECT m " +
            "FROM Meal m " +
            "WHERE m.user.id = :userId " +
            "AND YEAR(m.date) = :year " +
            "AND MONTH(m.date) = :month " +
            "AND m.isFavorite = false " +
            "ORDER BY m.date ASC")
    List<Meal> findMealsByUserIdAndYearAndMonth(Long userId, int year, int month);

    @Query ("SELECT new com.dailySync.meal.dto.MealDayCntResDto(m.date, WEEK(m.date), COUNT(m.date) as CNT) " +
            "FROM Meal m " +
            "WHERE m.user.id = :userId " +
            "AND YEAR(m.date) = :year " +
            "AND MONTH(m.date) = :month " +
            "AND m.isFavorite = false " +
            "GROUP BY m.date " +
            "ORDER BY m.date ASC")
    List<MealDayCntResDto> findDayByUserIdAndYearAndMonth(Long userId, int year, int month);

    List<Meal> findByUserId(Long userId);

    List<Meal> findByUserIdAndIsFavorite(Long userId, boolean favorite);
}
