package com.dailySync.schedule.repository;

import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.entities.ScheduleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {

    // 특정 날짜의 일정 조회
    List<ScheduleDetail> findByDate(LocalDate date);
}
