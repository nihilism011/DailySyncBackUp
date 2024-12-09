package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemResDto {

    private Long id;
    private String title;
    private Long groupId;
    private String day;
    private Integer itemOrder;
    private String status;
    private Boolean isAuto;

    public TodoItemResDto(Long id, String title, String day, Integer itemOrder, String status, Boolean isAuto) {
    }

    public static TodoItemResDto of(Long id, String title, String day, Integer itemOrder, String status, Boolean isAuto) {
        return new TodoItemResDto(id, title, day, itemOrder, status, isAuto );
    }

    public static TodoItemResDto of(TodoItem todoItem) {
        TodoItemResDto dto = new TodoItemResDto();
        dto.setId(todoItem.getId());
        dto.setTitle(todoItem.getTitle());
        dto.setDay(todoItem.getDay());
        dto.setItemOrder(todoItem.getItemOrder());
        dto.setStatus(todoItem.getStatus());
        dto.setIsAuto(todoItem.isAuto());
        dto.setGroupId(todoItem.getTodoGroup().getId());
        return dto;
    }



}
