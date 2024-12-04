package com.dailySync.todo.controller;//package com.dailySync.todo.controller;
//
//import com.dailySync.todo.dto.TodoGroupReqDto;
//import com.dailySync.todo.dto.TodoItemReqDto;
//import com.dailySync.todo.dto.TodoListReqDto;
//import com.dailySync.todo.entities.TodoGroup;
//import com.dailySync.todo.entities.TodoItem;
//import com.dailySync.todo.entities.TodoList;
//import com.dailySync.todo.service.TodoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("api/todo")
//public class TodoController {
//    final private TodoService todoService;
//
//    @GetMapping("group/view/{userId}")
//    public List<TodoGroup> getTodoGroups(@PathVariable ("userId") Long userId) {
//        return todoService.getTodoGroupsByUserId(userId);
//    }
//
//    @GetMapping("/item/view/{userId}")
//    public List<TodoItem> getTodoItems(@PathVariable ("userId") Long userId, @PathVariable ("groupId") Long groupId) {
//        return todoService.getTodoItemsByUserIdAndGroupId(userId, groupId);
//    }
//
//    @GetMapping("list/view/today/{userId}")
//    public List<TodoList> getTodoListsForToday(@PathVariable ("userId") Long userId) {
//        return todoService.getTodoListsByUserIdAndToday(userId);
//    }
//
//    /** todoList 추가하기*/
//    @PostMapping("list/create/{userId}/{itemId}")
//    public ResponseEntity<?> addTodoList(
//            @PathVariable ("userId") Long userId,
//            @PathVariable ("itemId") Long itemId,
//            @ModelAttribute TodoListReqDto reqDto) {
//        return ResponseEntity.ok(todoService.createTodoList(userId, itemId, reqDto));
//    }
//    /** todoGroup 추가하기*/
//    @PostMapping("group/create/{userId}")
//    public ResponseEntity<?> addTodoGroup(@PathVariable ("userId") Long userId, @ModelAttribute TodoGroupReqDto reqDto) {
//        return ResponseEntity.ok(todoService.createTodoGroup(userId, reqDto));
//    }
//    /** todoItem 추가하기*/
//    @PostMapping("item/create/{userId}/{groupId}")
//    public ResponseEntity<?> addTodoItem(
//            @PathVariable ("userId") Long userId,
//            @PathVariable ("groupId") Long groupId,
//            @ModelAttribute TodoItemReqDto reqDto) {
//        return ResponseEntity.ok(todoService.createTodoItem(userId, groupId, reqDto));
//    }
//    /** todoItem 삭제하기*/
//    @DeleteMapping("item/delete/{id}")
//    public ResponseEntity<?> deleteTodoItem(@PathVariable ("id") Long id) {
//        todoService.deleteItemById(id);
//        return ResponseEntity.ok().build();
//        }
//    /** todoList 삭제하기*/
//    @DeleteMapping("list/delete/{id}")
//    public ResponseEntity<?> deleteTodoList(@PathVariable ("id") Long id) {
//        todoService.deleteListById(id);
//        return ResponseEntity.ok().build();
//    }
//    /** todoGroup 삭제하기*/
//    @DeleteMapping("group/delete/{id}")
//    public ResponseEntity<?> deleteTodoGroup(@PathVariable ("id") Long id) {
//        todoService.deleteGroupById(id);
//        return ResponseEntity.ok().build();
//    }
//
//
//
//}
