package com.dailySync.todo.controller;//package com.dailySync.todo.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.todo.dto.*;
import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/todo")
public class TodoController {
    final private TodoService todoService;

    // 조회

    //userId에 해당하는 todoGroup을 조회 (5)
    @GetMapping("/Group/{userId}")
    public ResponseEntity<?> getTodoList(@PathVariable ("userId") Long userId){
        return ResponseEntity.ok(todoService.getTodoGroup(userId));
    }


    //todoGroup을 생성 (6)
    @PostMapping("/Group")
    public ResponseEntity<?> addTodoGroup(@RequestBody TodoGroupReqDto reqDto) {
        return ApiResponse.success(todoService.createTodoGroup(reqDto));
    }

    //todoItem을 생성 (7)
    @PostMapping("/Item")
    public ResponseEntity<?> addTodoItem(@RequestBody TodoItemReqDto reqDto) {
        return ApiResponse.success(todoService.createTodoItem(reqDto));
    }
    //------------------------------------------------------------------------

    //TodoList
    //TodoList 목록 조회(날짜기준)
    @GetMapping("/list")
    public ResponseEntity<?> getTodoList ( @PathVariable Long userId, @PathVariable LocalDate date) {
        List<TodoListResDto> result = todoService.getTodoList(userId, date);
        return ResponseEntity.ok(result);
    }
    //TodoList 삭제
    @DeleteMapping("list/{id}")
    public ResponseEntity<Void> deleteTodoList(@PathVariable Long id){
        todoService.deleteTodoList(id);
        return ResponseEntity.ok().build();
    }
    //todoList 항목 수정
    @PutMapping("/todoList/{id}")
    public ResponseEntity<TodoListResDto> updateTodoList(@PathVariable Long id, @ModelAttribute TodoListReqDto todoListReqDto) {
        TodoListResDto result = todoService.updateTodoList(id, todoListReqDto);
        return  ResponseEntity.ok(result);
    }




//    @PostMapping("/list")
//    public ResponseEntity<TodoListResDto> createTodoList(@ModelAttribute TodoListReqDto reqDto) {
//        TodoListResDto result = todoService.createTodoList(reqDto);
//        return ResponseEntity.ok(result);
//    }



}
