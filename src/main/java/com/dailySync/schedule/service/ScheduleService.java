package com.dailySync.schedule.service;

import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.repository.ScheduleRepository;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

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

    /**title로 일정찾기*/

    /**날짜로 일정찾기*/
    public List<ScheduleResDto> getScheduleDate(Long userId, int year, int month){
        List<Schedule> scheduleList = scheduleRepository.findByUserId_YearAndMonth(userId, year, month);
        return scheduleList.stream().map(Schedule::toResDto).toList();
    }








 }
