package com.dailySync.user.entities;

import com.dailySync.account.entity.Account;
import com.dailySync.common.BaseEntity;
import com.dailySync.meal.entities.Meal;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
import com.dailySync.todo.entities.TodoList;
import com.dailySync.todo.entities.TodoMemo;
import com.dailySync.user.dto.UserReqDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @NotBlank
    @Column (unique = true, nullable = false)
    @Size (min = 2, max = 20)
    private String userName;

    @NotBlank
    @Column (nullable = false)
    private String password;

    @NotBlank
    @Column (nullable = false)
    @Size (min = 1, max = 10)
    private String gender;

    @Email
    @Column (unique = true, nullable = false)
    private String email;

    @Column
    private LocalDateTime lastLogin;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    private UserSetting userSetting;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<TodoItem> todoItems;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<TodoMemo> todoMemos;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<TodoGroup> todoGroups;
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<TodoList> todoLists;
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meal> meals;
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    public User(String userName, String password, String gender) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
    }

    public static User of(UserReqDto userInfo, String encodedPassword) {
        return User.builder()
                .email(userInfo.getEmail())
                .userName(userInfo.getUserName())
                .gender(userInfo.getGender())
                .password(encodedPassword)
                .build();
    }

    public void update(UserReqDto reqDto) {
        if (reqDto.getUserName() != null && !reqDto.getUserName().isEmpty()) {
            this.userName = reqDto.getUserName();
        }
        if (reqDto.getEmail() != null && !reqDto.getEmail().isEmpty()) {
            this.email = reqDto.getEmail();
        }
        if (reqDto.getGender() != null && !reqDto.getGender().isEmpty()) {
            this.gender = reqDto.getGender();
        }
    }

}
