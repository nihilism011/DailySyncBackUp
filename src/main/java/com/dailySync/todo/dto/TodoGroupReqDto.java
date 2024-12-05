package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
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

    public TodoGroupReqDto(String title, String description) {
    }

    public static TodoGroupReqDto of (String title, String description) {
        return new TodoGroupReqDto(title, description);
    }

    public static TodoGroupReqDto of(Long userId, String title, String description) {
        return new TodoGroupReqDto(userId, title, description);
    }



}

