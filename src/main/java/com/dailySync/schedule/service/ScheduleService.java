package com.dailySync.schedule.service;

import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    final private ScheduleRepository scheduleRepository;

    /**아이디로 유저 일정 가져오기*/
    public List<ScheduleResDto> getUser(Long userId){
        List<Schedule> ScheduleList = scheduleRepository.findByUserId(userId);

        /**List<ScheduleResDto> dtoList = new ArrayList<>();

            for (Schedule schedule : ScheduleList){
            dtoList.add(ScheduleResDto.of(schedule));
        }
        return dtoList;*/

        return scheduleRepository.findByUserId(userId).stream().map(ScheduleResDto::of).toList();


 }

    /**title,날짜로 일정찾기*/
    public List<ScheduleResDto> searchSchedule(String title, String startTime, String endTime){
        List<Schedule> ScheduleList = scheduleRepository.findByTitleOrStartTimeOrEndTime(title, startTime, endTime);
        return scheduleRepository.findByTitleOrStartTimeOrEndTime(title, startTime, endTime).stream().map(ScheduleResDto::of).toList();
    }








 }
