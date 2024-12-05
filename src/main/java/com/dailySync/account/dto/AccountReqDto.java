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
@Schema (description = "가계부 항목 요청 DTO")
public class AccountReqDto {
    @Schema (description = "항목 제목", example = "점심 식사")
    private String title;
    @Schema (description = "가계부 항목 날짜 (yyyy-MM-dd)", example = "2024-12-05")
    private LocalDate accountDate;
    @Schema (description = "카테고리", example = "식비")
    private String category;
    @Schema (description = "금액", example = "15000")
    private Integer amount;
    @Schema (description = "설명", example = "회사 점심 비용")
    private String description;
    @Schema (description = "고정 항목 여부", example = "false")
    private Boolean Fixed;
}

