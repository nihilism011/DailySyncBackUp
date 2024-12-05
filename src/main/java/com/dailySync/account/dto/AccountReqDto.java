package com.dailySync.account.dto;

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
    private String category;
    private LocalDate accountDate;
    private String title;
    private String description;
    private Integer amount;
    private Boolean Fixed;
}

