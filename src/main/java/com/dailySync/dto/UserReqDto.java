package com.dailySync.dto;

import com.dailySync.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReqDto { //받고싶은 정보
    private Long id;
    private String userName;
    private String password;
    private String gender;
    private String email;
    private String name;


//    public User toUser() {
//
//        return User.builder()
//                .userName(this.userName)
//                .password(this.password)
//                .gender(this.gender)
//                .email(this.email)
//                .name(this.name)
//                .build();
//    }
}
