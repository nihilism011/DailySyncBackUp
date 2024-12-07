package com.dailySync.account.entity;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.common.BaseEntity;
import com.dailySync.constant.AccountCategory;
import com.dailySync.user.entities.User;
import jakarta.persistence.*;
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
    @Enumerated (value = EnumType.STRING)
    private AccountCategory category;
    @Column (nullable = false)
    private LocalDate accountDate;
    @Column (nullable = false)
    private String title;
    @Column
    private String description;
    @Column (nullable = false)
    private Integer amount;
    @Column (nullable = false)
    private Boolean fixed;

    public static AccountResDto toResDto(Account account) {
        return AccountResDto.builder().
                id(account.getId()).
                title(account.getTitle()).
                amount(account.getAmount()).
                accountDate(account.getAccountDate()).
                description(account.getDescription()).
                category(account.getCategory()).
                build();
    }

    public static Account of(User user, AccountReqDto reqDto) {
        return Account.builder().
                user(user).
                accountDate(reqDto.getAccountDate()).
                amount(reqDto.getAmount()).
                category(reqDto.getCategory()).
                description(reqDto.getDescription()).
                title(reqDto.getTitle()).
                fixed(reqDto.getFixed()).
                build();
    }

    public void update(AccountReqDto reqDto) {
        if (reqDto.getCategory() != null) {
            this.category = reqDto.getCategory();
        }
        if (reqDto.getAccountDate() != null) {
            this.accountDate = reqDto.getAccountDate();
        }
        if (reqDto.getTitle() != null) {
            this.title = reqDto.getTitle();
        }
        if (reqDto.getDescription() != null) {
            this.description = reqDto.getDescription();
        }
        if (reqDto.getAmount() != null) {
            this.amount = reqDto.getAmount();
        }
        if (reqDto.getFixed() != null) {
            this.fixed = reqDto.getFixed();
        }
    }
}
