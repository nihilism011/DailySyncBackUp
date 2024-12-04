package com.dailySync.account.entity;

import com.dailySync.account.dto.AccountReqDto;
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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteAccount extends BaseEntity {
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
    @Column (nullable = true)
    private String category;
    @Column (nullable = false)
    private String title;
    @Column (nullable = true)
    private String description;
    @Column (nullable = false)
    private Integer amount;

    public static FavoriteAccount of(User user, AccountReqDto reqDto){
        return FavoriteAccount.builder().
                user(user).
                title(reqDto.getTitle()).
                category(reqDto.getCategory()).
                amount(reqDto.getAmount()).
                description(reqDto.getDescription()).
                build();
    }
}
