package com.dailySync.schedule.service;

import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    final private ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedule(Long id){
        return scheduleRepository.findByUserId(id);
    }
}
