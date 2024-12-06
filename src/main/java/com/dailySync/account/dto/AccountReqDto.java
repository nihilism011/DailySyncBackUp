package com.dailySync.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String category;
    private Integer amount;
    private String description;
    private Boolean Fixed;
}

