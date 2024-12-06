package com.dailySync.schedule.repository;

import com.dailySync.schedule.entities.Schedule;
import com.dailySync.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
//CRUD 등의 기능을 제공하는 JpaRepository를 상속받음

    List<Schedule> findByUserId(Long userId);
    List<Schedule> findByTitle(String Title);
    // 특정 사용자와 날짜 범위에 맞는 일정 조회



}