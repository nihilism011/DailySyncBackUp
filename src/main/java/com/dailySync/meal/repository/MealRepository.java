package com.dailySync.meal.repository;

import com.dailySync.meal.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    @Query ("SELECT m FROM Meal m WHERE m.user.id = :userId AND YEAR(m.date) = :year AND MONTH(m.date) = :month")
    List<Meal> findMealsByUserIdADNYearAndMonth(Long userId, int year, int month);

    List<Meal> findByUserId(Long userId);

    List<Meal> findByUserIdAndIsFavorite(Long userId, boolean favorite);
}
