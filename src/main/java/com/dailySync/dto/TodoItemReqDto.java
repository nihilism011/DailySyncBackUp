package com.dailySync.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoItemReqDto {

    private String day;
    private Integer order;
    private String title;
    private String status;

}
