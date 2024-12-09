package com.dailySync.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemReqDto {

    private Long userId;
    private Long groupId;
    private List<String> day;
    private Integer itemOrder;
    private String title;
    private Integer isAuto;
    private String status;
}
