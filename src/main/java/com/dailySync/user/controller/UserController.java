package com.dailySync.user.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.common.CommonService;
import com.dailySync.user.dto.LoginRequest;
import com.dailySync.user.dto.TokenRequest;
import com.dailySync.user.dto.UserInfoDto;
import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api")
public class UserController {
    final private CommonService commonService;
    final private UserService userService;

    /**
     * {@code 로그인}
     */
    @Tag (name = "USER", description = "로그인")
    @Operation (summary = "로그인")
    @PostMapping ("login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return ApiResponse.success(commonService.login(loginRequest));
    }

    /**
     * {@code 회원가입}
     */
    @Tag (name = "USER", description = "회원가입")
    @Operation (summary = "회원가입")
    @PostMapping ("signup")
    public ResponseEntity<ApiResponse<Boolean>> joinUser(@RequestBody UserReqDto reqDto) {
        return ApiResponse.success(userService.createUser(reqDto));
    }

    /**
     * {@code JWT 재발급}
     */
    @Tag (name = "USER", description = "JWT 리프레시")
    @Operation (summary = "JWT 리프레시")
    @PostMapping ("token/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(@RequestBody TokenRequest token) throws Exception {
        return ApiResponse.success(commonService.refreshToken(token.getToken()));
    }

    /**
     * {@code 유저 정보 수정}
     */
    @Tag (name = "USER", description = "유저 정보 수정")
    @Operation (summary = "유저 정보 수정")
    @PutMapping ("user/info")
    public ResponseEntity<ApiResponse<Boolean>> updateUser(
            @RequestBody UserInfoDto userInfo) throws Exception {
        Long userId = commonService.getUserId();
        return ApiResponse.success(userService.updateUser(userId, userInfo));
    }

    /**
     * {@code 유저 비밀번호 수정}
     */
    @Tag (name = "USER", description = "유저 비밀번호 수정")
    @Operation (summary = "아직 웹에는 구현 안함")
    @PatchMapping ("user/password")
    public ResponseEntity<ApiResponse<Boolean>> updateUserPassword(
            @RequestBody UserReqDto reqDto) throws Exception {
        Long userId = commonService.getUserId();
        return ApiResponse.success(userService.updateUserPassword(userId, reqDto));
    }

    /**
     * {@code 유저 정보}
     */
    @Tag (name = "USER", description = "유저 정보")
    @Operation (summary = "유저 정보 불러오기")
    @GetMapping ("user/info")
    public ResponseEntity<ApiResponse<UserInfoDto>> getUserInfo() throws Exception {
        Long userId = commonService.getUserId();
        return ApiResponse.success(userService.getUserInfo(userId));
    }

    @Tag (name = "USER", description = "유저 삭제")
    @Operation (summary = "유저 회원 탈퇴")
    @DeleteMapping ("user")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser()  {
        Long userId = commonService.getUserId();
        return ApiResponse.success(userService.deleteUser(userId));
    }
}