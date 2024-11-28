package com.dailySync.service;

import com.dailySync.dto.UserReqDto;
import com.dailySync.dto.UserResDto;
import com.dailySync.entities.User;
import com.dailySync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    //@Autowired 와 같음
    final private UserRepository userRepository;





    public List<UserResDto> getAllUser(){
//        [userResDto1,userResDto2...]

        List<User> list = userRepository.findAll();
        // select *
        // from user

//        [user1,user2...]
        List<UserResDto> resList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);

            UserResDto userResDto = UserResDto.of(user);

            resList.add(userResDto);
        }

        return resList;

//        return userRepository.findAll().stream().map(UserResDto::of).toList();
    }

    public UserResDto findUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        return UserResDto.of(user);
    }
    public UserResDto findUserName(String userName){
        User user = userRepository.findByUserName(userName);
        return UserResDto.of(user);
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
