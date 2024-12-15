package com.dailySync.user.service;

import com.dailySync.config.JwtUtil;
import com.dailySync.constant.ResMessage;
import com.dailySync.user.dto.LoginRequest;
import com.dailySync.user.dto.UserInfoDto;
import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.entities.User;
import com.dailySync.user.entities.UserSetting;
import com.dailySync.user.repository.UserRepository;
import com.dailySync.user.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    final private JwtUtil jwtUtil;

    /**
     * {@code 로그인}
     */
    public String login(LoginRequest request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
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
        UserSetting userSetting = new UserSetting(user);
        userSettingRepository.save(userSetting);
        return true;
    }

    /**
     * {@code 토큰 재발급}
     */
    public String refreshToken(String token) throws Exception {
        if (jwtUtil.validateToken(token)) {
            Long userId = jwtUtil.extractUserId(token);
            User user = userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            return jwtUtil.generateToken(user);
        } else {
            return null;
        }
    }

    /**
     * {@code 유저 정보 수정}
     */
    public boolean updateUser(Long userId, UserReqDto userReqDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        user.update(userReqDto);
        userRepository.save(user);
        return true;
    }

    /**
     * {@code 유저 비밀번호 수정}
     */
    public boolean updateUserPassword(Long userId, UserReqDto userReqDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        String encodedPassword = passwordEncoder.encode(userReqDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return true;
    }

    public UserInfoDto getUserInfo(Long userId) throws Exception {
        User user = getUser(userId);
        UserSetting setting = userSettingRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        return UserInfoDto.of(user,setting);
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User getUser(Long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
    }
}
