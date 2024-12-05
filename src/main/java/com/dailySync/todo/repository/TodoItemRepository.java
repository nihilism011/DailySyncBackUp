package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoItem;
import com.dailySync.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByDayAndUserId(String day, Long userId);

    // 유저와 그룹의 "new" 상태인 TodoItem을 생성일자 기준으로 오름차순 정렬
    List<TodoItem> findByUserIdAndTodoGroupIdOrderByCreatedAtAsc(Long userId, Long groupId);

    // 유저의 특정 요일에 해당하는 TodoItem을 우선순위 기준으로 오름차순 정렬
    List<TodoItem> findByUserIdAndDayOrderByItemOrderAsc(Long userId, String day);
}
