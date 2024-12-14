package com.dailySync.user.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.user.dto.LoginRequest;
import com.dailySync.user.dto.TokenRequest;
import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api")
public class UserController {
    final private UserService userService;

    /**
     * {@code 로그인}
     */
    @PostMapping ("login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return ApiResponse.success(userService.login(loginRequest));
    }

    /**
     * {@code 회원가입}
     */
    @PostMapping ("signup")
    public ResponseEntity<ApiResponse<Boolean>> joinUser(@RequestBody UserReqDto reqDto) {
        return ApiResponse.success(userService.createUser(reqDto));
    }

    /**
     * {@code JWT 재발급}
     */
    @PostMapping ("token/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(@RequestBody TokenRequest token) throws Exception {
        return ApiResponse.success(userService.refreshToken(token.getToken()));
    }

    /**
     * {@code 유저 정보 수정}
     */
    @PatchMapping ("user/userId")
    public ResponseEntity<ApiResponse<Boolean>> updateUser(
            @RequestBody UserReqDto reqDto) throws Exception {
        Long userId = getUserId();
        return ApiResponse.success(userService.updateUser(userId, reqDto));
    }

    /**
     * {@code 유저 비밀번호 수정}
     */
    @PatchMapping ("user/userId")
    public ResponseEntity<ApiResponse<Boolean>> updateUserPassword(
            @RequestBody UserReqDto reqDto) throws Exception {
        Long userId = getUserId();
        return ApiResponse.success(userService.updateUserPassword(userId, reqDto));
    }

    private Long getUserId(){
        return  (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}