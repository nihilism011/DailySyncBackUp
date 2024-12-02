package com.dailySync.service;


import com.dailySync.dto.UserTestReqDto;
import com.dailySync.dto.UserTestResDto;
import com.dailySync.entities.UserTest;
import com.dailySync.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTestService {

    //@Autowired 와 같음
    final private UserTestRepository userTestRepository;


    public List<UserTestResDto> getAllUser() {
//        [userResDto1,userResDto2...]

        List<UserTest> list = userTestRepository.findAll();

//        select *
//        from user
//        [user1,user2...]
        List<UserTestResDto> resList = new ArrayList<>();

        for (UserTest user : list) {
            resList.add(UserTestResDto.of(user));
        }

        return resList;
//        return userRepository.findAll().stream().map(UserResDto::of).toList();
    }

    //    아이디로 유저 찾기
    public UserTestResDto findUser(Long id) {

        UserTest user = userTestRepository.findById(id).orElse(null);
//        Optional<User> userO = userRepository.findById(id);
//        User userU = userO.orElse(null);
        return UserTestResDto.of(user);
    }

    //    이름으로 유저 찾기
    public UserTestResDto findUserName(String userName) {
        UserTest user = userTestRepository.findByUserName(userName).orElse(null);
        return UserTestResDto.of(user);
    }

    //유저 추가
    public boolean insertUser(UserTestReqDto userReqDto) {
//        userReqDto.toUser();
        UserTest user = UserTest.of(userReqDto);
        UserTest insrtedUser = userTestRepository.save(user);
        return true;
    }
    public boolean updateUser(Long id, UserTestReqDto userReqDto) {
        try {
            UserTest user = userTestRepository.findById(id).orElse(null);
            if (user == null) {
                return false;
            }
            user.update(userReqDto);
            userTestRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(Long id){


        userTestRepository.deleteById(id);


        return true;
    }

}
