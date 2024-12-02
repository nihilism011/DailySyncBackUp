package com.dailySync.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReqDto {
    private String userName;
    private String name;
    private String password;
    private String gender;
    private String email;

//    public User toUser(){
//        return User.builder()
//                .userName(this.userName)
//                .password(this.password)
//                .gender(this.gender)
//                .email(this.email)
//                .name(this.name)
//                .build();
//    }
}