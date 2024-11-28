package com.dailySync.entities;

import com.dailySync.dto.UserReqDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String userName;

    public void update(UserReqDto userReqDto){
        this.userName = userReqDto.getUserName();
    }
}
