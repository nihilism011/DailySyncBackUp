package com.dailySync.schedule.repository;

import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule s WHERE s.user_id = :userId AND YEAR(s.start_time) = :year AND MONTH(s.start_time) = :month ORDER BY s.start_time ASC", nativeQuery = true)
    List<Schedule> findByUserIdAndStartTimeYearAndStartTimeMonthOrderByStartTimeAsc(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

    List<Schedule> findByUserIdAndTitleContainingOrderByStartTimeAsc(Long userId, String title);

    @Query(value = "SELECT s.* FROM schedule s "
            + "WHERE s.user_id = :userId "
            + "AND (YEAR(s.start_time) = :year OR YEAR(s.end_time) = :year) "
            + "ORDER BY s.start_time ASC", nativeQuery = true)
    List<Schedule> findByUserId_Year(@Param("userId") Long userId, @Param("year") int year);

    @Query(value = "SELECT s.* FROM schedule s "
            + "WHERE s.user_id = :userId "
            + "AND (YEAR(s.start_time) = :year AND MONTH(s.start_time) = :month "
            + "     OR YEAR(s.end_time) = :year AND MONTH(s.end_time) = :month) "
            + "ORDER BY s.start_time ASC", nativeQuery = true)
    List<Schedule> findByUserId_YearAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT s.* FROM schedule s "
            + "WHERE s.user_id = :userId "
            + "AND s.start_time >= :startTime "
            + "AND s.end_time <= :endTime "
            + "ORDER BY s.start_time ASC", nativeQuery = true)
    List<Schedule> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);



}

