package com.dailySync.service;

import com.dailySync.dto.TodoItemReqDto;
import com.dailySync.entities.User;
import com.dailySync.repository.TodoRepository;
import com.dailySync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    final private TodoRepository todoRepository;
    final private UserRepository userRepository;

    public boolean addTodoItem(Long userId, TodoItemReqDto reqDto){
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            System.out.println(user);
            System.out.println(user.getTodoItems());
        } else {
            System.out.println("User not found");
        }
        return true;
    }
}
