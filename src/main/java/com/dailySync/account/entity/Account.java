package com.dailySync.account.entity;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.common.BaseEntity;
import com.dailySync.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column
    private String category;
    @Column (nullable = false)
    private LocalDate accountDate;
    @Column (nullable = false)
    private String title;
    @Column
    private String description;
    @Column (nullable = false)
    private Integer amount;
    @Column (nullable = false)
    private boolean isFixed;

    public static AccountResDto toResDto(Account account) {
        return AccountResDto.builder().
                title(account.getTitle()).
                amount(account.getAmount()).
                accountDate(account.getAccountDate()).
                description(account.getDescription()).
                category(account.getCategory()).
                build();
    }
    public static Account of(User user,AccountReqDto reqDto){
        return Account.builder().
                user(user).
                accountDate(reqDto.getAccountDate()).
                amount(reqDto.getAmount()).
                category(reqDto.getCategory()).
                description(reqDto.getDescription()).
                title(reqDto.getTitle()).
                build();
    }
}
