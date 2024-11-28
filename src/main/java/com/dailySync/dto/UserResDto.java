package com.dailySync.dto;

import com.dailySync.entities.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResDto {
    private Long id;
    private String userName;

    public static UserResDto of(User user){
        return UserResDto.builder()
                .userName(user.getUserName())
                .build();
    }
}
