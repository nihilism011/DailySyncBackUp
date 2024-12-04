package com.dailySync.test.repository;

import com.dailySync.test.entities.TodoItemTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoTestRepository extends JpaRepository<TodoItemTest, Long> {
}
