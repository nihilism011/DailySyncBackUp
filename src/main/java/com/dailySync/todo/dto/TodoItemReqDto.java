package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemReqDto {

    private Long userId;
    private Long groupId;
    private String day;
    private Integer itemOrder;
    private String title;
    private Boolean isAuto;
    private String status;
}
