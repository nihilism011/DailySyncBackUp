package com.dailySync.todo.entities;

import com.dailySync.common.BaseEntity;
import com.dailySync.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoList extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "item_id")
    private TodoItem todoItem;

    @Column
    private LocalDate date;  //To.Do 리스트의 날짜를 저장

    @Column
    private LocalDateTime checkedTime; // 리스트가 체크된 시간을 저장

    @Column
    private Integer listOrder;// (1~3 또는 고정 우선순위 4)

    @Column
    private String title; // 변동항목 타이틀

    @Column
    private String status;



}
