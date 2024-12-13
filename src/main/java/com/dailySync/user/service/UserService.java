package com.dailySync.user.service;

import com.dailySync.config.JwtUtil;
import com.dailySync.constant.ResMessage;
import com.dailySync.user.dto.LoginRequest;
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
    final private JwtUtil jwtUtil;

    public String login(LoginRequest request) throws Exception{
        User user = userRepository.findByUserName(request.getUsername()).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        if (!request.getPassword().equals(user.getPassword())){
            throw new Exception(ResMessage.NOT_MATCH_PASSWORD);
        }
        return jwtUtil.generateToken(user);
    }

    public List<UserResDto> getAllUser() {
                return userRepository.findAll().stream().map(UserResDto::of).toList();
    }

    //    아이디로 유저 찾기
    public UserResDto findUser(Long id) {

        User user = userRepository.findById(id).orElse(null);
        //        Optional<User> userO = userRepository.findById(id);
        //        User userU = userO.orElse(null);
        return UserResDto.of(user);
    }

    //    이름으로 유저 찾기
    public UserResDto findUserName(String userName) throws Exception{
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
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

    public boolean deleteUser(Long id) {

        userRepository.deleteById(id);

        return true;
    }

    //이름과 이메일 두 가지를 입력 받고 비밀번호 찾기
    public UserResDto findPasswordWithNameAndEmail(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email).orElse(null);
        return UserResDto.of(user);
    }
}
