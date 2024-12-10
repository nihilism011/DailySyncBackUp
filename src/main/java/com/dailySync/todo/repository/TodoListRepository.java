package com.dailySync.todo.repository;

import com.dailySync.todo.entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    // 날짜에 해당하는 TodoList를 가져오고, 우선순위(listOrder) 기준으로 오름차순 정렬
    List<TodoList> findByUserIdAndDateOrderByListOrderAsc(Long userId, LocalDate date);

    // 날짜에 대한 총 TodoList 수를 세는 쿼리
    long countByUserIdAndDate(Long userId, LocalDate date);

    // 날짜에 대해 완료된 TodoList(checkedTime이 있는) 수를 세는 쿼리
    long countByUserIdAndDateAndCheckedTimeIsNull(Long userId, LocalDate date);

    boolean existsByUserIdAndDateAndTodoItemId(Long userId, LocalDate date, Long todoItemId);
}
