package com.dailySync.common;

import com.dailySync.account.service.AccountService;
import com.dailySync.config.JwtUtil;
import com.dailySync.constant.ResMessage;
import com.dailySync.user.dto.LoginRequest;
import com.dailySync.user.entities.User;
import com.dailySync.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final UserService userService;
    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginRequest request) throws Exception {
        User user = userService.getUser(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception(ResMessage.NOT_MATCH_PASSWORD);
        }
        accountService.moveFixedAccountItem(user);
        userService.updateLastLoginDate(user);
        return jwtUtil.generateToken(user);
    }

    /**
     * {@code 토큰 재발급}
     */
    public String refreshToken(String token) throws Exception {
        if (jwtUtil.validateToken(token)) {
            Long userId = jwtUtil.extractUserId(token);
            User user = userService.getUser(userId);
            accountService.moveFixedAccountItem(user);
            userService.updateLastLoginDate(user);
            return jwtUtil.generateToken(user);
        } else {
            return null;
        }
    }

    public Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
