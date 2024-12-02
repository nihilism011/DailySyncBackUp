package com.dailySync.service;

import com.dailySync.dto.TodoItemReqDto;
import com.dailySync.entities.UserTest;
import com.dailySync.repository.TodoTestRepository;
import com.dailySync.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoTestService {
    final private TodoTestRepository todoTestRepository;
    final private UserTestRepository userTestRepository;

    public boolean addTodoItem(Long userId, TodoItemReqDto reqDto){
        UserTest user = userTestRepository.findById(userId).orElse(null);
        if (user != null) {
            System.out.println(user);
            System.out.println(user.getTodoItems());
        } else {
            System.out.println("User not found");
        }
        return true;
    }
}
