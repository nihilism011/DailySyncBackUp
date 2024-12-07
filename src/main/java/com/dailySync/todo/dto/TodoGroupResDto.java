package com.dailySync.todo.dto;

import com.dailySync.todo.entities.TodoGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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



    public static TodoGroupResDto of(TodoGroup todoGroup) {
        TodoGroupResDto dto = new TodoGroupResDto();
        dto.setId(todoGroup.getId());
        dto.setTitle(todoGroup.getTitle());
        dto.setDescription(todoGroup.getDescription());
        dto.setUserId(todoGroup.getUser().getId());  // userId 가져오기
        return dto;
    }
    public static TodoGroupResDto oft(TodoGroup todoGroup) {
        TodoGroupResDto dto = new TodoGroupResDto();
        dto.setTitle(todoGroup.getTitle());
        dto.setDescription(todoGroup.getDescription());
        return dto;
    }


}
