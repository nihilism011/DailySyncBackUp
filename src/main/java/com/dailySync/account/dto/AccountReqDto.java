package com.dailySync.account.dto;

import com.dailySync.constant.AccountCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountReqDto {
    private String title;
    private LocalDate accountDate;
    private AccountCategory category;
    private Integer amount;
    private String description;
    private Boolean Fixed;
}

