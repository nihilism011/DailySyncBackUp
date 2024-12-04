package com.dailySync.user.controller;

import com.dailySync.user.dto.UserReqDto;
import com.dailySync.user.dto.UserResDto;
import com.dailySync.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @GetMapping ("user")
    public ResponseEntity<?> getAllUser() {
        List<UserResDto> list = userService.getAllUser();
        return ResponseEntity.ok(list);
    }

    /**
     * 주소창
     * db저장소에 저장한다. 겟올유저 로
     * 리스트에 유저서비스에서 getalluser함수로 만든거 담는다
     * 리턴한다.
     */
    @GetMapping ("user/userId/{id}")
    public ResponseEntity<?> findByIdUser(@PathVariable ("id") Long userId) {
        return ResponseEntity.ok(userService.findUser(userId));
    }

    @GetMapping ("user/userName/{userName}")
    public ResponseEntity<?> findByIdUser(@PathVariable ("userName") String userName) {
        return ResponseEntity.ok(userService.findUserName(userName));
    }

    //    form-data 로 데이터를 받을때
    @PostMapping ("user")
    public ResponseEntity<?> joinUser(@ModelAttribute UserReqDto reqDto) {
        return ResponseEntity.ok(userService.insertUser(reqDto));
    }

    //    @GetMapping
    //    @PostMapping
    //    @PatchMapping
    //    @PutMapping
    //    @DeleteMapping
    //유저 업데이트
    //    url에서 데이터를 받을 경우 +
    //    json 형태로 데이터를 받을 경우
    @PatchMapping ("user/userId/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable ("id") Long id,
            @RequestBody UserReqDto reqDto) {

        return ResponseEntity.ok(userService.updateUser(id, reqDto));
    }
}