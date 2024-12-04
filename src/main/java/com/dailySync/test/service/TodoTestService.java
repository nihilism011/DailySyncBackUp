package com.dailySync.test.service;

import com.dailySync.test.dto.TodoItemReqDto;
import com.dailySync.test.entities.UserTest;
import com.dailySync.test.repository.TodoTestRepository;
import com.dailySync.test.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoTestService {
    final private TodoTestRepository todoTestRepository;
    final private UserTestRepository userTestRepository;

    public boolean addTodoItem(Long userId, TodoItemReqDto reqDto) {
        UserTest user = userTestRepository.findById(userId).orElse(null);
        if (user != null) {
            System.out.println(user);
            System.out.println(user.getTodoItemTests());
        } else {
            System.out.println("User not found");
        }
        return true;
    }
}
