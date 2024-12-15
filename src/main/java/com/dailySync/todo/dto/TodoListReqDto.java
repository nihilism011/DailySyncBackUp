package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoItem;
import com.dailySync.user.entities.User;
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
    private Integer listOrder;
    private String title;
    private LocalDateTime checkedTime;


}
