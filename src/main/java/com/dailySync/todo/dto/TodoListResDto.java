package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoListResDto {

    private Long id;
    private Long itemId;
    private Long groupId;
    private String date;
    private Integer listOrder;
    private String title;
    private LocalDateTime checkedTime;
    private String groupTitle;
    private LocalDateTime createdAt;
    private String status;
    private List<TodoCountResponseDto> todoCounts;  // 날짜별 통계 리스트

    public TodoListResDto(Long id, Long aLong, String string, Integer listOrder, String title) {
    }

    // 기존 of2 메소드
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

    // 기존 of3 메소드
    public static TodoListResDto of3(TodoList todoList) {
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

    // 기존 of 메소드
    public static TodoListResDto of(TodoList todoList) {
        TodoListResDto dto = new TodoListResDto();
        dto.setId(todoList.getId());
        dto.setTitle(todoList.getTitle());
        dto.setListOrder(todoList.getListOrder());
        dto.setDate(String.valueOf(todoList.getDate()));
        dto.setCheckedTime(todoList.getCheckedTime());
        dto.setCreatedAt(todoList.getCreatedAt());
        dto.setStatus(todoList.getStatus());
        if (todoList.getTodoItem() != null) {
            dto.setItemId(todoList.getTodoItem().getId());
            dto.setGroupTitle(todoList.getTodoItem().getTodoGroup().getTitle());
            dto.setGroupId(todoList.getTodoItem().getTodoGroup().getId());
        } else {
            dto.setItemId(null);
        }
        return dto;
    }

}