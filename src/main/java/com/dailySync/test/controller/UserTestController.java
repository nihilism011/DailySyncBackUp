package com.dailySync.test.controller;

import com.dailySync.test.dto.UserTestReqDto;
import com.dailySync.test.dto.UserTestResDto;
import com.dailySync.test.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserTestController {
    final private UserTestService userService;

    @GetMapping ("Test/user")
    public ResponseEntity<?> getAllUser() {
        List<UserTestResDto> list = userService.getAllUser();
        return ResponseEntity.ok(list);
    }

    // 주소창
    // db저장소에 저장한다. 겟올유저 로
    // 리스트에 유저서비스에서 getalluser함수로 만든거 담는다
    // 리턴한다.
    @GetMapping ("Test/user/userId/{id}")
    public ResponseEntity<?> findByIdUser(@PathVariable ("id") Long userId) {
        return ResponseEntity.ok(userService.findUser(userId));
    }

    @GetMapping ("Test/user/userName/{userName}")
    public ResponseEntity<?> findByIdUser(@PathVariable ("userName") String userName) {
        return ResponseEntity.ok(userService.findUserName(userName));
    }

    //    form-data 로 데이터를 받을때
    @PostMapping ("Test/user")
    public ResponseEntity<?> joinUser(@ModelAttribute UserTestReqDto reqDto) {
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
    @PatchMapping ("Test/user/userId/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable ("id") Long id,
            @ModelAttribute UserTestReqDto reqDto) {

        return ResponseEntity.ok(userService.updateUser(id, reqDto));
    }
}