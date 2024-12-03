package com.dailySync.user.service;

import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.dto.UserResDto;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    //@Autowired 와 같음
    final private UserRepository userRepository;


    public List<UserResDto> getAllUser() {
//        [userResDto1,userResDto2...]

        List<User> list = userRepository.findAll();

//        select *
//        from user
//        [user1,user2...]
        List<UserResDto> resList = new ArrayList<>();

        for (User user : list) {
            resList.add(UserResDto.of(user));
        }

        return resList;
//        return userRepository.findAll().stream().map(UserResDto::of).toList();
    }

    //    아이디로 유저 찾기
    public UserResDto findUser(Long id) {

        User user = userRepository.findById(id).orElse(null);
//        Optional<User> userO = userRepository.findById(id);
//        User userU = userO.orElse(null);
        return UserResDto.of(user);
    }

    //    이름으로 유저 찾기
    public UserResDto findUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElse(null);
        return UserResDto.of(user);
    }

    //유저 추가
    public boolean insertUser(UserReqDto userReqDto) {
//        userReqDto.toUser();
        User user = User.of(userReqDto);
        User insrtedUser = userRepository.save(user);
        return true;
    }
    public boolean updateUser(Long id, UserReqDto userReqDto) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return false;
            }
            user.update(userReqDto);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(Long id){


        userRepository.deleteById(id);


        return true;
    }

}
