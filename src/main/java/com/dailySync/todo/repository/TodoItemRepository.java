package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByTodoGroupId(Long groupId);

    List<TodoItem> findByUserIdAndTodoGroupId(Long userId, Long groupId);
}
