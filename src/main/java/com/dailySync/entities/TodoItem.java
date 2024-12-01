package com.dailySync.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItem extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column
    private String day;

    @Column (nullable = false)
    private Integer sortOrder;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String status;

}
