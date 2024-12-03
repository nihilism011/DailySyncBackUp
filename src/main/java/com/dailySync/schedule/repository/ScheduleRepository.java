package com.dailySync.schedule.repository;

import com.dailySync.schedule.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long userId);

    @Query("SELECT "
            + "CAST(SUBSTRING(s.startTime, 1, 4) AS INT) AS startYear, "
            + "CAST(SUBSTRING(s.startTime, 6, 2) AS INT) AS startMonth, "
            + "CAST(SUBSTRING(s.endTime, 1, 4) AS INT) AS endYear, "
            + "CAST(SUBSTRING(s.endTime, 6, 2) AS INT) AS endMonth "
            + "FROM Schedule s "
            + "WHERE s.userId = :userId")
    List<Schedule> findByUserId_YearAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);



}