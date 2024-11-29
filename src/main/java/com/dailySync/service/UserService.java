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
        //findById 내장함수. Optional 타입을 반환
        return UserResDto.of(user);
        //return UserResDto.of(userRepository.findById(id).orElse(null));
    }
    public UserResDto findUserName(String userName){
        User user = userRepository.findByUserName(userName);
        return UserResDto.of(user);
    }

    public boolean insertUser(UserReqDto userReqDto){
        //try {
            //User user = userReqDto.to();
            //User insertedUser = userRepository.save(user);
            User user = User.of(userReqDto);
            userRepository.save(user);
            //save: 데이터베이스에 user 넣어준다.
            return true;
        //}

//       catch (Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }



    }


    public boolean updateUser(Long id, UserReqDto userReqDto){
        try{
            User user = userRepository.findById(id).orElse(null);
            //기존에 있는애 찾아오면 update
            //User user = new User(); -> insert
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

    public boolean deleteUser(Long id){

       // User user = userRepository.findById(id).orElse(null);

       // userRepository.delete(user);
        // 객체 단위

        userRepository.deleteById(id);

        return true;
    }

}
