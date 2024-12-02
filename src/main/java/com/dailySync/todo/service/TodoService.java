package com.dailySync.todo.service;

import com.dailySync.todo.dto.TodoItemReqDto;
import com.dailySync.todo.repository.TodoRepository;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
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
