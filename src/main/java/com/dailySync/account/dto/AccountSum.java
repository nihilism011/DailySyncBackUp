package com.dailySync.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSum {
    private LocalDate accountDate;
    private Long plusSumAmount;
    private Long minusSumAmount;

    public AccountSum(LocalDate date){
        this.accountDate = date;
        this.plusSumAmount = 0L;
        this.minusSumAmount = 0L;
    }
}
