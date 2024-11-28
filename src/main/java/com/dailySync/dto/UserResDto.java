package com.dailySync.dto;

import com.dailySync.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResDto {
    private Long id;
    private String userName;

    public static UserResDto of(User user){
        //UserResDto 객체 생성
        UserResDto userResDto = new UserResDto();
        //UserResDto 생성한 객체에 매개변수로 받은 User 객체에 있던 userName 정보를 입력.
        userResDto.setUserName(user.getUserName());
        //UserResDto 객체 리턴.
        return userResDto;
    }
}
