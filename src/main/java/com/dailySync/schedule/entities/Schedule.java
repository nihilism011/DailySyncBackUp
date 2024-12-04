package com.dailySync.schedule.entities;

import com.dailySync.common.BaseEntity;
import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends BaseEntity {

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column (nullable = false)
    private LocalDateTime startTime;

    @Column (nullable = false)
    private LocalDateTime endTime;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String description;

    public static ScheduleResDto toResDto(Schedule schedule) {
        return ScheduleResDto.builder().
                title(schedule.getTitle()).
                description(schedule.getDescription()).
                startTime(schedule.getStartTime()).
                endTime(schedule.getEndTime()).
                build();
    }

    public static Schedule of(User user, ScheduleReqDto reqDto) {
        return Schedule.builder().
                user(user).
                title(reqDto.getTitle()).
                startTime(reqDto.getStartTime()).
                endTime(reqDto.getEndTime()).
                build();
    }

}
