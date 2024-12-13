package com.dailySync.user.service;

import com.dailySync.config.JwtUtil;
import com.dailySync.constant.ResMessage;
import com.dailySync.user.dto.LoginRequest;
import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.dto.UserResDto;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    final private JwtUtil jwtUtil;

    /**
     * {@code 로그인}
     */
    public String login(LoginRequest request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user);
        }
        throw new Exception(ResMessage.NOT_MATCH_PASSWORD);
    }

    /**
     * {@code  회원가입}
     */
    public boolean createUser(UserReqDto userReqDto) {
        String encodedPassword = passwordEncoder.encode(userReqDto.getPassword());
        User user = User.of(userReqDto, encodedPassword);
        userRepository.save(user);
        return true;
    }

    public List<UserResDto> getAllUser() {
        return userRepository.findAll().stream().map(UserResDto::of).toList();
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

}
