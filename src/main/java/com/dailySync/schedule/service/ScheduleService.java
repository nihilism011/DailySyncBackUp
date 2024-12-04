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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**일정 뿌려주기 */
//    public ScheduleResDto getAllUserScheduleList(int year, int month){
//        List<Schedule>
//        return
//    }


    /**아이디로 유저 일정 가져오기*/
    public List<ScheduleResDto> getUser(Long userId){
        List<Schedule> scheduleList = scheduleRepository.findByUserId(userId);
        return scheduleRepository.findByUserId(userId).stream().map(ScheduleResDto::of).toList();


 }

    /**title로 일정찾기*/
    public List<ScheduleResDto> getScheduleTitle(Long userId, String title){
        List<Schedule> scheduleList = scheduleRepository.findByUserId_Title(userId, title);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }

    /**month로 일정찾기*/
    public List<ScheduleResDto> getScheduleYear(Long userId, int year){
        List<Schedule> scheduleList = scheduleRepository.findByUserId_Year(userId, year);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }

    /**month,year로 일정찾기*/
    public List<ScheduleResDto> getScheduleDate(Long userId, int year, int month){
        List<Schedule> scheduleList = scheduleRepository.findByUserId_YearAndMonth(userId, year, month);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }








 }
