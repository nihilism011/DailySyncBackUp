package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {
    // 유저의 TodoGroup을 생성일자를 기준으로 오름차순 정렬해서 가져오는 쿼리
    List<TodoGroup> findByUserIdOrderByCreatedAtAsc(Long userId);

}
