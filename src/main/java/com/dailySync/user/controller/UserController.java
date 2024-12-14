package com.dailySync.user.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.user.dto.LoginRequest;
import com.dailySync.user.dto.TokenRequest;
import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.dto.UserResDto;
import com.dailySync.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping ("token/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(@RequestBody TokenRequest token) throws Exception{
        System.out.println(token.getToken());
        return ApiResponse.success(userService.refreshToken(token.getToken()));
    }

    @GetMapping ("user")
    public ResponseEntity<?> getAllUser() {
        List<UserResDto> list = userService.getAllUser();
        return ResponseEntity.ok(list);
    }

    @PatchMapping ("user/userId/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable ("id") Long id,
            @RequestBody UserReqDto reqDto) {

        return ResponseEntity.ok(userService.updateUser(id, reqDto));
    }

}