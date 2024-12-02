package com.dailySync.todo.entities;

import com.dailySync.common.BaseEntity;
import com.dailySync.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoList extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column
    private LocalDate date;

    @Column
    private Long todoID;

    @Column
    private LocalDateTime checkedTime;

    @Column
    private Integer listOrder;


}
