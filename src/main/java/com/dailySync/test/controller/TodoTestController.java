package com.dailySync.test.controller;

import com.dailySync.test.dto.TodoItemReqDto;
import com.dailySync.test.service.TodoTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoTestController {
    final private TodoTestService todoService;

    @PostMapping ("Test/todo/userId/{userId}")
    public ResponseEntity<?> addTodo(@PathVariable ("userId") Long userId, @ModelAttribute TodoItemReqDto reqDto) {
        System.out.println(userId);
        System.out.println(reqDto);
        return ResponseEntity.ok(todoService.addTodoItem(userId,reqDto));
    }
}
