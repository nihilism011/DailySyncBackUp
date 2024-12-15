package com.dailySync.todo.repository;

import com.dailySync.todo.dto.TodoItemResDto;
import com.dailySync.todo.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    // 유저와 그룹의 "new" 상태인 TodoItem을 생성일자 기준으로 오름차순 정렬
    List<TodoItem> findByUserIdAndTodoGroupIdAndStatusOrderByCreatedAtAsc(Long userId, Long groupId, String status);

    List<TodoItem> findByUserIdAndStatusAndIsAuto(Long userId, String status, boolean isAuto);

    @Query("SELECT t FROM TodoItem t WHERE t.user.id = :userId AND t.day = :day")
    List<TodoItem> findByUserIdAndDay(@Param("userId") Long userId, @Param("day") String day);


}
