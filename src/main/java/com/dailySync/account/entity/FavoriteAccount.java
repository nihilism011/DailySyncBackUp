package com.dailySync.account.entity;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.FavorAccountResDto;
import com.dailySync.common.BaseEntity;
import com.dailySync.constant.AccountCategory;
import com.dailySync.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteAccount extends BaseEntity {
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
    @Column (nullable = false)
    @Enumerated (value = EnumType.STRING)
    private AccountCategory category;
    @Column (nullable = false)
    private String title;
    @Column
    private String description;
    @Column (nullable = false)
    private Integer amount;

    public static FavoriteAccount of(User user, AccountReqDto reqDto) {
        return FavoriteAccount.builder().
                user(user).
                title(reqDto.getTitle()).
                category(reqDto.getCategory()).
                amount(reqDto.getAmount()).
                description(reqDto.getDescription()).
                build();
    }

    public FavorAccountResDto toResDto() {
        return FavorAccountResDto.builder().
                id(this.getId()).
                category(this.category).
                amount(this.amount).
                description(this.description).
                title((this.title)).
                build();
    }

    public void update(AccountReqDto reqDto) {
        if (reqDto.getCategory() != null) {
            this.category = reqDto.getCategory();
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
    }
}
