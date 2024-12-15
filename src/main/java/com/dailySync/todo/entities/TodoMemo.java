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
public class TodoMemo extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 5000)
    private String contents;




}
