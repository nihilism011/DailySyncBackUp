package com.dailySync.schedule.service;

import com.dailySync.schedule.dto.ScheduleReqDto;
import com.dailySync.schedule.dto.ScheduleResDto;
import com.dailySync.schedule.entities.Schedule;
import com.dailySync.schedule.entities.ScheduleDetail;
import com.dailySync.schedule.repository.ScheduleDetailRepository;
import com.dailySync.schedule.repository.ScheduleRepository;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleDetailRepository scheduleDetailRepository;

    /**유저 일정 가져오기*/
    public List<ScheduleResDto> getUser(Long userId, int year, int month) {
        List<Schedule> scheduleList = scheduleRepository.findByUserIdAndStartTimeYearAndStartTimeMonthOrderByStartTimeAsc(userId, year, month);
        return scheduleList.stream().map(ScheduleResDto::of).collect(Collectors.toList());
    }

    /**id로 일정 가져오기*/
    public List<ScheduleResDto> getSchedule(Long userId, Long id) {
        List<Schedule> scheduleList = scheduleRepository.findByUserIdAndId(userId, id);
        return scheduleList.stream().map(ScheduleResDto::of).collect(Collectors.toList());
    }

    /**title로 일정찾기*/
    public List<ScheduleResDto> getScheduleTitle(Long userId, String title) {
        List<Schedule> scheduleList = scheduleRepository.findByUserIdAndTitleContainingOrderByStartTimeAsc(userId, title);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }

    /**month로 일정찾기*/
    public List<ScheduleResDto> getScheduleYear(Long userId, int year) {
        List<Schedule> scheduleList = scheduleRepository.findByUserId_Year(userId, year);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }

    /**year,month로 일정찾기*/
    public List<ScheduleResDto> getScheduleDate(Long userId, int year, int month) {
        List<Schedule> scheduleList = scheduleRepository.findByUserId_YearAndMonth(userId, year, month);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }

    //**기간설정으로 일정찾기 */
    public List<ScheduleResDto> getScheduleInRange(Long userId, LocalDate startTime, LocalDate endTime) {
        LocalDateTime startOfDay = startTime.atStartOfDay();  // 시작 시간 00:00:0
        LocalDateTime endOfDay = endTime.atTime(23, 59, 59, 999999999);
        List<Schedule> scheduleList = scheduleRepository.findByUserIdAndDateRange(userId, startOfDay, endOfDay);
        return scheduleList.stream().map(Schedule::toResDto).collect(Collectors.toList());
    }

    /**일정추가*/
    public boolean addSchedule(Long userId, ScheduleReqDto reqDto) throws Exception{
        User user = userRepository.findById(6L).orElseThrow(() -> new Exception("user doesn't exist"));
        scheduleRepository.save(Schedule.of(user, reqDto));
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

    //일정 삭제하기
    public boolean deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
        return true;
    }

    public ScheduleService(UserRepository userRepository, ScheduleRepository scheduleRepository, ScheduleDetailRepository scheduleDetailRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleDetailRepository = scheduleDetailRepository;
    }

    public List<ScheduleDetail> getSchedulesByDate(LocalDate date) {
        return scheduleDetailRepository.findByDate(date);
    }




}