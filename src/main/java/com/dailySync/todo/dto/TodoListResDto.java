package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListResDto {

    private Long id;
    private Long itemId;
    private String date;
    private Integer listOrder;
    private String title;

    public static TodoListResDto of2(TodoList todoList) {
        TodoListResDto dto = new TodoListResDto();
        dto.setListOrder(todoList.getListOrder());
        dto.setTitle(todoList.getTitle());
        if (todoList.getTodoItem() != null) {
            dto.setItemId(todoList.getTodoItem().getId());
        } else {
            dto.setItemId(null);
        }
        return dto;
    }
    public static TodoListResDto of(TodoList todoList) {
        TodoListResDto dto = new TodoListResDto();
        dto.setId(todoList.getId());
        dto.setTitle(todoList.getTitle());
        dto.setListOrder(todoList.getListOrder());
        dto.setDate(String.valueOf(todoList.getDate()));
        if (todoList.getTodoItem() != null) {
            dto.setItemId(todoList.getTodoItem().getId());
        } else {
            dto.setItemId(null);
        }
        return dto;
    }
}