package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemResDto {

    private Long id;
    private String title;
    private Long groupId;
    private List<String> day;
    private Integer itemOrder;
    private String status;
    private Boolean isAuto;
    private String groupTitle;

    public TodoItemResDto(Long id, String title, List<String> day, String status) {
    }


    public static TodoItemResDto of3(TodoItem todoItem) {
        return new TodoItemResDto(
                todoItem.getId(),
                todoItem.getTitle(),
                todoItem.getDay(),
                todoItem.getStatus()
        );
    }
    
    public TodoItemResDto(Long id, String title, List<String> day, Integer itemOrder, String status, Boolean isAuto) {
    }

    public static TodoItemResDto of(Long id, String title, List<String> day, Integer itemOrder, String status, Boolean isAuto) {
        return new TodoItemResDto(id, title, day, itemOrder, status, isAuto );
    }

    public static TodoItemResDto of(TodoItem todoItem) {
        TodoItemResDto dto = new TodoItemResDto();
        dto.setId(todoItem.getId());
        dto.setTitle(todoItem.getTitle());
        dto.setDay(todoItem.getDay());
        dto.setItemOrder(todoItem.getItemOrder());
        dto.setStatus(todoItem.getStatus());
        dto.setIsAuto(todoItem.getIsAuto());
        dto.setGroupId(todoItem.getTodoGroup().getId());
        dto.setGroupTitle(todoItem.getTodoGroup().getTitle());
        return dto;
    }



}
