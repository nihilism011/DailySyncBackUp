package com.dailySync.user.service;

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

    /**
     * {@code 유저 마지막 로그인 시간 업데이트}
     */
    public void updateLastLoginDate(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
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
     * {@code 유저 정보 수정}
     */
    public boolean updateUser(Long userId, UserInfoDto userInfo) throws Exception {
        User user = getUser(userId);
        user.update(userInfo);
        userRepository.save(user);
        UserSetting setting = user.getUserSetting();
        setting.update(userInfo);
        userSettingRepository.save(setting);
        return true;
    }

    /**
     * {@code 유저 비밀번호 수정}
     */
    public boolean updateUserPassword(Long userId, LoginRequest reqDto) throws Exception {
        User user = getUser(userId);
        String encodedPassword = passwordEncoder.encode(reqDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return true;
    }

    /**
     * {@code 유저정보 불러오기}
     */
    public UserInfoDto getUserInfo(Long userId) throws Exception {
        User user = getUser(userId);
        UserSetting setting = userSettingRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        return UserInfoDto.of(user, setting);
    }

    /**
     * {@code 유저 비밀번호 확인}
     */
    public boolean checkPassword(Long userId, LoginRequest request) throws Exception {
        User user = getUser(userId);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User getUser(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
    }

    public User getUser(Long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
    }
}
