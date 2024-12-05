package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemReqDto {

    private Long userId;
    private Long groupId;
    private String day;
    private Integer itemOrder;
    private String title;
    private Boolean isAuto;
    private String status;

    // of 메서드 추가
    public static TodoItemReqDto of(Long userId, Long groupId, String day, Integer itemOrder, String title, Boolean isAuto, String status) {
        return new TodoItemReqDto(userId, groupId, day, itemOrder, title, isAuto, status);
    }

}
