package com.dailySync.service;

import com.dailySync.dto.UserReqDto;
import com.dailySync.dto.UserResDto;
import com.dailySync.entities.User;
import com.dailySync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public List<UserResDto> getAllUser(){
        return userRepository.findAll().stream().map(UserResDto::of).toList();
    }
    public boolean putUser(Long id, UserReqDto userReqDto){
        try{
            User user = userRepository.findById(id).orElse(null);
            if(user == null){
                return false;
            }
            user.update(userReqDto);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
