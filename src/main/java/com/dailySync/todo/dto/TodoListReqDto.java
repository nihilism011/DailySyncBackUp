package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListReqDto {

    private Long userId;
    private Long itemId;
    private LocalDate date;
    private LocalDateTime checkedTime;
    private Integer listOrder;
}
