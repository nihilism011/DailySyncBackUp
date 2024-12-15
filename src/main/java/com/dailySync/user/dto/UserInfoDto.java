package com.dailySync.user.dto;

import com.dailySync.user.entities.User;
import com.dailySync.user.entities.UserSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private String userName;
    private String gender;
    private Integer weight;
    private Integer height;
    private Integer recommendCal;

    public static UserInfoDto of(User user, UserSetting setting){
        return UserInfoDto.builder()
                .userName(user.getUserName())
                .gender(user.getGender())
                .weight(setting.getWeight())
                .height(setting.getHeight())
                .recommendCal(setting.getRecommendCal())
                .build();
    }
}
