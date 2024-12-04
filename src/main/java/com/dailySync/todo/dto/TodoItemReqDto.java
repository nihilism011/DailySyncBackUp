package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemReqDto {

    private Long userId;
    private Long groupId;
    private String day;
    private Integer itemOrder;
    private String title;
    private String status;

}
