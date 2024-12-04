package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByUserIdAndDate(Long userId, LocalDate date);
}
