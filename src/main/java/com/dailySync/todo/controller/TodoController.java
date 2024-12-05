package com.dailySync.todo.controller;//package com.dailySync.todo.controller;

import com.dailySync.common.ApiResponse;
import com.dailySync.todo.dto.*;
import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
import com.dailySync.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
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

    //putMapping
    @Operation
            (
                    summary = " todoList 체크 ( checktiem null 일시 now() 반대도 마찬가지 )",
                    description =" ... "
            )
    @PutMapping("/list/update/check/{id}")
    public ResponseEntity<TodoListResDto> updateCheckList(@PathVariable Long id) {
        TodoListResDto updateCheck = todoService.updateListCheck(id);
        return ResponseEntity.ok(updateCheck);
    }

    @Operation
    (
            summary = " todoItem 삭제시(클라이언트입장)  status값  old로 변경 ",
            description =" ... "
    )
    @PutMapping("/item/update/status/{id}")
    public ResponseEntity<TodoItemResDto> updateStatus(@PathVariable Long id) {
        TodoItemResDto updateItem = todoService.updateStatus(id);
        return ResponseEntity.ok(updateItem);
    }

    @Operation
    (
            summary = " todoItem 정보 변경시 기존id status old처리 / 그후 새로생성 ",
            description =" ... "
    )
    @PutMapping("/item/update/{id}")
    public ResponseEntity<TodoItemResDto> updateItem(@PathVariable Long id, @RequestBody TodoItemReqDto reqDto) {
        TodoItemResDto updatedItem = todoService.updateItem(id, reqDto);
        return ResponseEntity.ok(updatedItem);
    }
    @Operation
    (
            summary = " 그룹정보 변경 ",
            description =" ... "
    )
    @PutMapping("/group/update/{id}")
    public ResponseEntity<TodoGroupResDto> updateGroup(@PathVariable Long id, @RequestBody TodoGroupReqDto reqDto) {
        TodoGroupResDto updatedItem = todoService.updateGroup(id, reqDto);
        return ResponseEntity.ok(updatedItem);
    }

    @Operation
    (
            summary = " todoList 변경",
            description =" ... "
    )
    @PutMapping("/list/update/{id}")
    public ResponseEntity<TodoListResDto> updateList(@PathVariable Long id, @RequestBody TodoListReqDto reqDto ) {
        TodoListResDto updateCheck = todoService.updateList(id, reqDto);
        return ResponseEntity.ok(updateCheck);
    }

    //GetMapping

    @Operation
    (
            summary = " userId, groupId 에 해당하는 todoItem 조회 ",
            description =" ... "
    )
    @GetMapping("/item/{userId}/{groupId}")
    public ResponseEntity<?> getTodoItem(@PathVariable ("userId") Long userId, @PathVariable ("groupId") Long groupId){
    return ResponseEntity.ok(todoService.getTodoItem(userId, groupId));
    }

    @Operation
    (
            summary = " userId에 해당하는 todoGroup을 조회 ",
            description =" ... "
    )
    @GetMapping("/group/{userId}")
    public ResponseEntity<?> getTodoGroup(@PathVariable ("userId") Long userId){
        return ResponseEntity.ok(todoService.getTodoGroup(userId));
    }
    @Operation
    (
            summary = " 금일 날짜 해당하는 todoList를 조회 ",
            description =" ... "
    )
    @GetMapping("/list/today/{userId}")
    public ResponseEntity<?> getTodayTodoList(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getTodayList(userId ));
    }
    @Operation
    (
            summary = " 날짜 해당하는 todoList를 조회  ",
            description =" ... "
    )
    @GetMapping("/list/{userId}/{date}")
    public ResponseEntity<?> getTodoListByDate(@PathVariable Long userId, @PathVariable LocalDate date) {
        return ResponseEntity.ok(todoService.getTodoList(userId, date));  // 날짜가 yyyy-MM-dd 형식으로 전달됨
    }

    //PostMapping

    @Operation
    (
            summary = " todoList에 변동일정 추가하기 listOrder(4) 고정 ",
            description =" ... "
    )
    @PostMapping("/list/change/")
    public ResponseEntity<?> addTodoList(@RequestBody TodoListReqDto reqDto) {
        return ApiResponse.success(todoService.createTodoList(reqDto));
    }

    @Operation
    (
            summary = " todoGroup 추가 ",
            description =" ... "
    )
    @PostMapping("/group")
    public ResponseEntity<?> addTodoGroup(@RequestBody TodoGroupReqDto reqDto) {
        return ApiResponse.success(todoService.createTodoGroup(reqDto));
    }

    @Operation
    (
            summary = " todoItem 추가 ",
            description =" ... "
    )
    @PostMapping("/item")
    public ResponseEntity<?> addTodoItem(@RequestBody TodoItemReqDto reqDto) {
        return ApiResponse.success(todoService.createTodoItem(reqDto));
    }

    //DeleteMapping

    @Operation
    (
            summary = " todoList 삭제 ",
            description =" ... "
    )
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Void> deleteTodoList(@PathVariable Long id){
        todoService.deleteTodoList(id);
        return ResponseEntity.ok().build();
    }

    @Operation
    (
            summary = " todoGroup 삭제 ",
            description =" ... "
    )
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteTodoGroup(@PathVariable Long id){
        todoService.deleteTodoGroup(id);
        return ResponseEntity.ok().build();
    }


    //------------------------------------------------------------------------

    //TodoList
    //TodoList 목록 조회(날짜기준)


}
