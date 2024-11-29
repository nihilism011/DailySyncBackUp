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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String email;

    public static  User of(UserReqDto reqDto){
        return User.builder()
                .name(reqDto.getName())
                .email(reqDto.getEmail())
                .userName(reqDto.getUserName())
                .gender(reqDto.getGender())
                .password(reqDto.getPassword())
                .build();
    }

    // = public Userof()


//    public void update(UserReqDto userReqDto){
//
//        this.userName = userReqDto.getUserName();
//    }

    public void update(UserReqDto reqDto){

        if (reqDto.getUserName() != null){
            this.userName = reqDto.getUserName();
        }
    }


}
