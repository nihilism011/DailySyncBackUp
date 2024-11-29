package com.dailySync.controller;

import com.dailySync.dto.UserReqDto;
import com.dailySync.dto.UserResDto;
import com.dailySync.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @GetMapping("user")
    public ResponseEntity<?> getAllUser() {
        List<UserResDto> list = userService.getAllUser();
        return ResponseEntity.ok(list);
    }

    @GetMapping("user/userId/{id}")
    public ResponseEntity<?> findByIdUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.findUser(userId));
    }

    @GetMapping("user/userName/{userName}")
    public ResponseEntity<?> findByIdUser(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(userService.findUserName(userName));
    }

//    form-data 로 데이터를 받을때
    @PostMapping("user")
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
    @PatchMapping("user/userId/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") Long id,
            @ModelAttribute UserReqDto reqDto) {

        return ResponseEntity.ok(userService.updateUser(id, reqDto));
    }
}