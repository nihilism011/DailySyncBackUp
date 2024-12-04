package com.dailySync.todo.entities;

import com.dailySync.common.BaseEntity;
import com.dailySync.user.entities.User;
import jakarta.persistence.*;
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
public class TodoItem extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "group_id", nullable = false)
    private TodoGroup todoGroup;

    @OneToMany(mappedBy = "todoItem",cascade = CascadeType.ALL)
    private List<TodoList> todoLists;

    @Column
    private String day; // To.Do 항목이 해당하는 요일을 저장

    @Column (nullable = false)
    private Integer itemOrder; // 항목의 우선순위를 저장

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String status;

}
