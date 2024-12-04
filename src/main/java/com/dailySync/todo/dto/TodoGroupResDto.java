package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoGroupResDto {

    private Long id;
    private Long userId;
    private String title;
    private String description;
    private boolean isAuto;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static TodoGroupResDto of(TodoGroup todoGroup) {
        if (todoGroup == null) {
            return new TodoGroupResDto();
        }
        return TodoGroupResDto.builder()
                .id(todoGroup.getId())
                .userId(todoGroup.getId())
                .title(todoGroup.getTitle())
                .description(todoGroup.getDescription())
                .isAuto(todoGroup.isAuto())
                .createdAt(todoGroup.getCreatedAt())
                .modifiedAt(todoGroup.getModifiedAt())
                .build();
    }
}
