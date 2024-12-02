package com.dailySync.schedule.service;

import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class scheduleService {

    final private ScheduleRepository scheduleRepositoryR;
}
