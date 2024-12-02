package com.dailySync.todo.entities;

import com.dailySync.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column
    private LocalDate date;

    @Column
    private Long todoID;

    @Column
    private LocalDateTime checkedTime;

    @Column
    private Integer listOrder;


}
