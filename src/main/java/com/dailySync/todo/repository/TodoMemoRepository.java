package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoMemoRepository extends JpaRepository<TodoMemo, Long> {

    Optional<TodoMemo> findByUserId(Long userId);
}
