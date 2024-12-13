package com.dailySync.todo.repository;

import com.dailySync.todo.dto.TodoCountResponseDto;
import com.dailySync.todo.entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    // 날짜에 해당하는 TodoList를 가져오고, 우선순위(listOrder) 기준으로 오름차순 정렬
    @Query("SELECT tl FROM TodoList tl " +
            "LEFT JOIN tl.todoItem ti " +
            "LEFT JOIN ti.todoGroup tg " +
            "WHERE tl.user.id = :userId " +
            "AND (tl.date = :date) " +
            "ORDER BY " +
            "    CASE WHEN ti.id IS NULL THEN 1 ELSE 0 END, " +
            "    tg.id ASC, " +
            "    tl.listOrder ASC")
    List<TodoList> findByUserIdAndDateOrderByListOrderAsc(Long userId, LocalDate date);

    @Query("SELECT tl FROM TodoList tl " +
            "LEFT JOIN tl.todoItem ti " +
            "LEFT JOIN ti.todoGroup tg " +
            "WHERE tl.user.id = :userId " +
            "AND (tl.date = :date OR (ti.id IS NULL)) " +
            "AND tl.status = 'new' " +
            "ORDER BY " +
            "    CASE WHEN tl.listOrder = 0 THEN 0 ELSE 1 END, " +
            "    CASE WHEN ti.id IS NULL THEN 1 ELSE 0 END, " +
            "    tg.id ASC, " +
            "    tl.listOrder ASC")
    List<TodoList> findByUserIdAndDateOrderByListOrderAndGroupId(Long userId, LocalDate date);
    //    // 날짜에 대한 총 TodoList 수를 세는 쿼리
    long countByUserIdAndDate(Long userId, LocalDate date);
    // 날짜에 대해 완료된 TodoList(checkedTime이 있는) 수를 세는 쿼리
    long countByUserIdAndDateAndCheckedTimeIsNull(Long userId, LocalDate date);

    boolean existsByUserIdAndDateAndTodoItemId(Long userId, LocalDate date, Long todoItemId);

    List<TodoList> findByUserIdAndTodoItemIsNullAndCheckedTimeIsNotNull(Long userId);

}