package com.dailySync.constant;

import lombok.Getter;

@Getter
public enum MealCategory {
    BREAKFAST("아침", "BREAKFAST"),
    LUNCH("점심", "LUNCH"),
    DINNER("저녁", "DINNER"),
    SUPPER("야식", "SUPPER"),
    SNACK("간식", "SNACK");

    private final String name;  // 카테고리 이름
    private final String eName;  // 카테고리 이름

    // 생성자
    MealCategory(String name, String eName) {
        this.name = name;
        this.eName = eName;
    }

    // 특정 카테고리 이름으로 Category를 찾는 메소드
    public static MealCategory fromString(String category) {
        for (MealCategory c : MealCategory.values()) {
            if (c.name().equalsIgnoreCase(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + category);
    }
}
