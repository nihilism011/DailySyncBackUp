package com.dailySync.schedule.entities;

import com.dailySync.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends BaseEntity {

    @ManyToOne
    @JoinColumn  (name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;
}
