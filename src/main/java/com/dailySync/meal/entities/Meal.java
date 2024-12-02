package com.dailySync.meal.entities;

import com.dailySync.entities.BaseEntity;
import com.dailySync.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private boolean isFavorite;
}
