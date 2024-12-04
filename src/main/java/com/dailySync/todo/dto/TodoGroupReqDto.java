package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoGroupReqDto {
    private Long userId;
    private String title;
    private String description;
    private boolean isAuto;
}
