package com.dailySync.constant;

import lombok.Getter;

@Getter
public enum AccountCategory {
    FOOD("식비", "#FFDDC1"),
    TRANSPORT("교통비", "#A8D0E6"),
    HOUSING("주거비", "#D4E157"),
    SALARY("월급", "#F5CBA7"),
    CULTURE("문화생활", "#F3A8B3"),
    MEDICAL("의료비", "#B2DFDB"),
    OTHER("기타", "#E1BEE7");

    // name을 반환하는 메소드
    private final String name;  // 카테고리 이름
    // color를 반환하는 메소드
    private final String color; // 배경 색상

    // 생성자
    AccountCategory(String name, String color) {
        this.name = name;
        this.color = color;
    }

    // 특정 카테고리 이름으로 Category를 찾는 메소드
    public static AccountCategory fromString(String category) {
        for (AccountCategory c : AccountCategory.values()) {
            if (c.name().equalsIgnoreCase(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + category);
    }
}
