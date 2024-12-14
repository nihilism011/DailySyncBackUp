package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoMemo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoMemoResDto {

    private Long id;
    private Long userId;
    private String contents;

    public static TodoMemoResDto of(TodoMemo todoMemo) {
        TodoMemoResDto dto = new TodoMemoResDto();
        dto.setUserId(todoMemo.getUser().getId());
        dto.setContents(todoMemo.getContents());
        dto.setId(todoMemo.getId());
        return dto;
    }
}
