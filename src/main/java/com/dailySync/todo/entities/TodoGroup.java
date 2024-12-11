package com.dailySync.todo.entities;

import com.dailySync.common.BaseEntity;
import com.dailySync.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TodoGroup extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @OneToMany (mappedBy = "todoGroup", cascade = CascadeType.ALL)
    private List<TodoItem> todoItems;

    @Column
    private String title;

    @Column (nullable = false)
    private String description;

    @Column// (기본 값 "new")
    private String status = "new";


}
