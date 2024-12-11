package com.dailySync.account.dto;

import com.dailySync.constant.AccountCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResDto {
    private Long id;
    private AccountCategory category;
    private LocalDate accountDate;
    private String title;
    private String description;
    private Integer amount;
    private Boolean fixed;
}
