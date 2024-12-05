package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoItem;
import com.dailySync.todo.entities.TodoList;
import com.dailySync.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListResDto {

    private Long id;
    private Long itemId;
    private String date;
    private Integer listOrder;

    public TodoListResDto(Long id, Long itemId, String date, Integer listOrder, String s) {
    }

    public TodoListResDto(Long id, String date, Integer listOrder) {
    }

    public static TodoListResDto of(Long id, Long itemId, String date, Integer listOrder) {
        return new TodoListResDto(id, itemId, date, listOrder);
    }
    public static TodoListResDto of(Long id,  String date, Integer listOrder) {
        return new TodoListResDto(id, date, listOrder);
    }
    public TodoListResDto of(User user, TodoItem todoItem, String date, Integer listOrder) {
        Long itemId = (todoItem != null) ? todoItem.getId() : null;
        return new TodoListResDto(user.getId(), itemId, date, listOrder, todoItem != null ? todoItem.getTitle() : null);
    }
}
