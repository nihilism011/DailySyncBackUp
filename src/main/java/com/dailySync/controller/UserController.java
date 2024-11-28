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
}

