package com.dailySync.schedule.repository;

import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.user.id = :userId AND YEAR(s.startTime) = :year AND MONTH(s.startTime) = :month")
    List<Schedule> userScheduleList(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

    @Query (value = "SELECT s.* FROM schedule s "
            + "WHERE s.user_id = :userId "
            + "AND s.title LIKE %:title%", nativeQuery = true)
    List<Schedule> findByUserId_Title(@Param ("userId") Long userId, @Param ("title") String title);

    @Query (value = "SELECT s.* FROM schedule s "
            + "WHERE s.user_id = :userId "
            + "AND (YEAR(s.start_time) = :year OR YEAR(s.end_time) = :year)", nativeQuery = true)
    List<Schedule> findByUserId_Year(@Param ("userId") Long userId, @Param ("year") int year);

    @Query (value = "SELECT s.* FROM schedule s "
            + "WHERE s.user_id = :userId "
            + "AND (YEAR(s.start_time) = :year AND MONTH(s.start_time) = :month "
            + "     OR YEAR(s.end_time) = :year AND MONTH(s.end_time) = :month)", nativeQuery = true)
    List<Schedule> findByUserId_YearAndMonth(@Param ("userId") Long userId, @Param ("year") int year, @Param ("month") int month);

}