package com.dailySync.schedule.service;

import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.repository.ScheduleRepository;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    final private ScheduleRepository scheduleRepository;
    final private UserRepository userRepository;

    //해당 사용자의 모든 일정 달력에 뿌리기
    public List<ScheduleResDto> AllSchedules(Long userId) {
        List<Schedule> list = scheduleRepository.findByUserId(userId);
        List<ScheduleResDto> resList = new ArrayList<>();
        for (Schedule schedule : list) {
            resList.add(ScheduleResDto.of(schedule));
        }
        return resList;
    }
    //일정 정보 추가하기
    public boolean insertSchedule (Long userId, ScheduleReqDto scheduleReqDto) {
        User user = userRepository.findById(userId).orElse(null);
        Schedule schedule = Schedule.of(user, scheduleReqDto);
        scheduleRepository.save(schedule);
        return true;
    }

    //일정 수정하기
    public boolean updateSchedule (Long id, ScheduleReqDto scheduleReqDto) {
        try{
            Schedule schedule = scheduleRepository.findById(id).orElse(null);
            if(schedule==null) {
                return false;
            }
            schedule.update(scheduleReqDto);
            scheduleRepository.save(schedule);
            return true;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //제목만으로 검색하여 관련 항목들이 모두 뜨도록 하기
    public List<ScheduleResDto> searchByTitle(String title) {
        List<Schedule> list = scheduleRepository.findByTitle(title);
        List<ScheduleResDto>  reList = new ArrayList<>();
        for(Schedule schedule : list) {
            reList.add(ScheduleResDto.of(schedule));
        }
        return reList;
    }

    //일정 삭제하기
    public boolean deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
        return true;
    }

    //날짜 클릭 시 해당 세부일정 불러오기


}




