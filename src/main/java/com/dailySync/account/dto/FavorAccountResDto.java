package com.dailySync.account.dto;

import com.dailySync.constant.AccountCategory;
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
    private AccountCategory category;
    private String title;
    private String description;
    private Integer amount;
}
