package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoGroupResDto {

    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String createdAt;

    public TodoGroupResDto(Long id, String title, String description, String createdAt) {
    }

    public static TodoGroupResDto of(Long id, String title, String description, String createdAt) {
        return new TodoGroupResDto(id, title, description, createdAt);
    }
    public static TodoGroupResDto of(Long id, Long userId, String title, String description, String createdAt) {
        return new TodoGroupResDto(id, userId , title, description, createdAt);
    }
    public static TodoGroupResDto of(TodoGroup todoGroup) {
        TodoGroupResDto dto = new TodoGroupResDto();
        dto.setId(todoGroup.getId());
        dto.setTitle(todoGroup.getTitle());
        dto.setDescription(todoGroup.getDescription());
        dto.setUserId(todoGroup.getUser().getId());  // userId 가져오기
        return dto;
    }

}
