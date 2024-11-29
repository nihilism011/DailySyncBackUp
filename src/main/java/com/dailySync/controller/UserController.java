package com.dailySync.controller;

import com.dailySync.dto.UserResDto;
import com.dailySync.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @GetMapping("user")
    public ResponseEntity<?> getAllUser(){
        List<UserResDto> list = userService.getAllUser();
        return ResponseEntity.ok(list);
    }

    @GetMapping("getuser")
    public ResponseEntity<?> findByIdUser(@RequestParam Long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping("getusername")
    public ResponseEntity<?> findByIdUser(@RequestParam String userName){
        return ResponseEntity.ok(userService.findUserName(userName));
    }

    //@ModelAttribute
    //@RequestBody

    @PatchMapping("user/userId/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") Long id,
            @ModelAttribute UserReqDto reqDto){
        if(reqDto == null){

            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(userService.updateUser(id, reqDto));
    }
    //@PathVariable : 동적 변수 값을 추출하여 메소드의 파라미터로 전달.

}

