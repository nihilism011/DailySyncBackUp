package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoGroupRepository extends JpaRepository<TodoGroup,Long> {
    List<TodoGroup> findByUserId(Long userId);
}
