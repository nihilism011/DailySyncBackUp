package com.dailySync.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavorAccountResDto {
    private Long id;
    private String category;
    private String title;
    private String description;
    private Integer amount;
}
