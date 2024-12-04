package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemResDto {

    private Long id;
    private Long userId;
    private Long groupId;
    private String day;
    private Integer itemOrder;
    private String title;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
