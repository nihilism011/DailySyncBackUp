package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoList;
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
    private String title;



    public TodoListResDto(Long id, String date, Integer listOrder) {
    }

    public TodoListResDto(Long id, Long itemId, String date, Integer listOrder) {
    }

    public static TodoListResDto of(Long id, Long itemId, String date, Integer listOrder) {
        return new TodoListResDto(id, itemId, date, listOrder);
    }
    public static TodoListResDto of(Long id,  String date, Integer listOrder) {
        return new TodoListResDto(id, date, listOrder);
    }
//ㅇ
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