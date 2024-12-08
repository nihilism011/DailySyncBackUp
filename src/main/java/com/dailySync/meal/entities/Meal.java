package com.dailySync.meal.entities;

import com.dailySync.common.BaseEntity;
import com.dailySync.meal.dto.MealReqDto;
import com.dailySync.meal.dto.MealResDto;
import com.dailySync.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column (nullable = false)
    private String foodName;

    @Column (nullable = false)
    private String category;

    @Column
    private String description;

    @Column (nullable = false)
    private String icon;

    @Column
    private Integer kcalories;

    @Column (nullable = false)
    private LocalDate date;

    @Column
    private Integer sugar;

    @Column
    private Integer sodium;

    @Column
    private Integer protein;

    @Column
    private Integer fat;

    @Column
    private Integer carbs;

    @Column (nullable = false)
    private Boolean isFavorite;

    // 유저의 리스트 조회시 사용
    public static MealResDto toRes(Meal meal) {
        LocalDate date = (LocalDate) meal.getDate();

        // 해당 날짜가 속한 주 번호 계산 (ISO 주 번호 기준)
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        // weekFields는 주단위가 1부터 MySQL은 0부터 주세는 단위가 1차이 나는 값을 빼준다.
        int week = date.get(weekFields.weekOfYear()) - 1;
        return MealResDto.builder()
                .id(meal.getId())
                .foodName(meal.getFoodName())
                .category(meal.getCategory())
                .description(meal.getDescription())
                .icon(meal.getIcon())
                .date(meal.getDate())
                .kcalories(meal.getKcalories())
                .sugar(meal.getSugar())
                .sodium(meal.getSodium())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .carbs(meal.getCarbs())
                .isFavorite(meal.getIsFavorite())
                .week(String.valueOf(week))
                .build();
    }

    // 추천내용 검색시 사용
    public static MealResDto toRecom(Meal meal) {
        return MealResDto.builder()
                .id(meal.getId())
                .foodName(meal.getFoodName())
                .category(meal.getCategory())
                .description(meal.getDescription())
                .icon(meal.getIcon())
                .date(meal.getDate())
                .kcalories(meal.getKcalories())
                .sugar(meal.getSugar())
                .sodium(meal.getSodium())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .carbs(meal.getCarbs())
                .build();
    }

    public static Meal ofSaveReq(User userId, Meal meal, boolean favorite) {
        return Meal.builder()
                .user(userId)
                .foodName(meal.getFoodName())
                .category(meal.getCategory())
                .description(meal.getDescription())
                .icon(meal.getIcon())
                .date(meal.getDate())
                .kcalories(meal.getKcalories())
                .sugar(meal.getSugar())
                .sodium(meal.getSodium())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .carbs(meal.getCarbs())
                .isFavorite(favorite)
                .build();
    }

    public void update(MealReqDto mealReqDto) {
        if (mealReqDto.getFoodName() != null) {
            this.foodName = mealReqDto.getFoodName();
        }
        if (mealReqDto.getCategory() != null) {
            this.category = mealReqDto.getCategory();
        }
        if (mealReqDto.getDescription() != null) {
            this.description = mealReqDto.getDescription();
        }
        if (mealReqDto.getIcon() != null) {
            this.icon = mealReqDto.getIcon();
        }
        if (mealReqDto.getDate() != null) {
            this.date = mealReqDto.getDate();
        }
        if (mealReqDto.getKcalories() != null) {
            this.kcalories = mealReqDto.getKcalories();
        }
        if (mealReqDto.getSugar() != null) {
            this.sugar = mealReqDto.getSugar();
        }
        if (mealReqDto.getSodium() != null) {
            this.sodium = mealReqDto.getSodium();
        }
        if (mealReqDto.getProtein() != null) {
            this.protein = mealReqDto.getProtein();
        }
        if (mealReqDto.getFat() != null) {
            this.fat = mealReqDto.getFat();
        }
        if (mealReqDto.getCarbs() != null) {
            this.carbs = mealReqDto.getCarbs();
        }
        if (mealReqDto.getIsFavorite() != null) {
            this.isFavorite = mealReqDto.getIsFavorite();
        }
    }
}
