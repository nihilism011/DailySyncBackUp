package com.dailySync.user.entities;

import com.dailySync.entities.BaseEntity;
import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
import com.dailySync.todo.entities.TodoList;
import com.dailySync.user.dto.UserReqDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<TodoItem> todoItems;
    private List<TodoGroup> todoGroups;
    private List<TodoList> todoLists;


    public static User of(UserReqDto reqDto) {
        return User.builder()
                .name(reqDto.getName())
                .email(reqDto.getEmail())
                .userName(reqDto.getUserName())
                .gender(reqDto.getGender())
                .password(reqDto.getPassword())
                .build();
    }

    public User(String userName, String password, String gender) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
    }

//    public void update(UserReqDto userReqDto) {
//        this.userName = userReqDto.getUserName();
//        this.name = userReqDto.getName();
//        this.email = userReqDto.getEmail();
//        this.gender = userReqDto.getGender();
//        this.password = userReqDto.getPassword();
//    }

    public void update(UserReqDto reqDto){
        if (reqDto.getUserName() != null) {
            this.userName = reqDto.getUserName();
        }
        if (reqDto.getName() != null) {
            this.name = reqDto.getName();
        }
        if (reqDto.getEmail() != null) {
            this.email = reqDto.getEmail();
        }
        if (reqDto.getGender() != null) {
            this.gender = reqDto.getGender();
        }
        if (reqDto.getPassword() != null) {
            this.password = reqDto.getPassword();
        }
    }

}
