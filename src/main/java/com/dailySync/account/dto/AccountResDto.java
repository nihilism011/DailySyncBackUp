package com.dailySync.account.dto;

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
    private String category;
    private LocalDate accountDate;
    private String title;
    private String description;
    private Integer amount;
}
