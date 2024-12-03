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

    //아이디로 유저 일정 가져오기
    public List<Schedule> getUser(Long userId){

        return scheduleRepository.findByUserId(userId);
    }

    //아이디로 일정정보를 월별로 가져오기
//    public List<Schedule> getSchedule(Long userId){
//        List<Schedule> list = scheduleRepository.
//
//
//    }







}
