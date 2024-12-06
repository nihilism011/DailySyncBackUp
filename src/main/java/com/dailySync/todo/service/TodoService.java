package com.dailySync.todo.service;//package com.dailySync.todo.service;


import com.dailySync.todo.dto.*;
import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
import com.dailySync.todo.entities.TodoList;
import com.dailySync.todo.repository.TodoGroupRepository;
import com.dailySync.todo.repository.TodoItemRepository;
import com.dailySync.todo.repository.TodoListRepository;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    final private UserRepository userRepository;
    final private TodoGroupRepository todoGroupRepository;
    final private TodoItemRepository todoItemRepository;
    final private TodoListRepository todoListRepository;


    // 날짜 받아와서 총개수 , null인개수 반환
    public TodoCountResponseDto getTodoCount(Long userId, LocalDate date){

        long nullCount = todoListRepository.countByUserIdAndDateAndCheckedTimeIsNull(userId, null);
        long allCount = todoListRepository.countByUserIdAndDate(userId, date);

        return new TodoCountResponseDto(nullCount, allCount);

    }
    //userId에 해당하는 todoGroup을 조회 (5)
    public List<TodoGroupResDto> getTodoGroup(Long userId) {
        List<TodoGroup> todoGroups = todoGroupRepository.findByUserIdOrderByCreatedAtAsc(userId);
        return todoGroups.stream()
                .map(TodoGroupResDto::of)
                .collect(Collectors.toList());
    }
    //userId, groupId 에 해당하는 todoItem 조회
    public List<TodoItemResDto> getTodoItem(Long userId, Long groupId) {
        List<TodoItem> todoItems
                = todoItemRepository.findByUserIdAndTodoGroupIdOrderByCreatedAtAsc(userId, groupId);
        return todoItems.stream().map(TodoItemResDto::of).collect(Collectors.toList());

    }
    public List<TodoListResDto> getTodayList(Long userId) {
        LocalDate today = LocalDate.now();
        List<TodoList> todoLists = todoListRepository.findByUserIdAndDateOrderByListOrderAsc(userId, today);
        return todoLists.stream()
                .map(TodoListResDto::of)
                .collect(Collectors.toList());
    }

    public TodoGroupResDto getTodoGroupInfo(Long id) {
        TodoGroup todoGroup = todoGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todogroup에 id값이 없엉"));
        return TodoGroupResDto.oft(todoGroup);
    }
    public TodoListResDto getTodoListInfo(Long id) {
        TodoList todolist = todoListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TodoList에 id값이 없엉"));
        return TodoListResDto.of2(todolist);
    }
    public TodoItemResDto getTodoItemInfo(Long id) {
        TodoItem todoitem = todoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TodoItem에 id값이 없엉"));
        return TodoItemResDto.of(todoitem);
    }


    public List<TodoListResDto> getTodoList(Long userId, LocalDate date) {
        List<TodoList> todoLists = todoListRepository.findByUserIdAndDateOrderByListOrderAsc(userId, date);
        return todoLists.stream()
                .map(TodoListResDto::of)
                .collect(Collectors.toList());
    }
    //생성

    //todolist을 생성 (7)
    public Boolean createTodoList(TodoListReqDto reqDto) {
        User user = userRepository.findById(reqDto.getUserId()).orElse(null);

        TodoList todoList = TodoList.builder()
                .user(user)
                .date(reqDto.getDate())
                .checkedTime(null)
                .title(reqDto.getTitle())
                .listOrder(4)
                .build();
        todoListRepository.save(todoList);
        return true;

    }


    //todoItem을 생성 (7)
    public Boolean createTodoItem(TodoItemReqDto reqDto) {
        User user = userRepository.findById(reqDto.getUserId()).orElse(null);
        TodoGroup todoGroup = todoGroupRepository.findById(reqDto.getGroupId()).orElse(null);

        TodoItem todoItem = TodoItem.builder()
                .user(user)
                .todoGroup(todoGroup)
                .day(reqDto.getDay())
                .itemOrder(reqDto.getItemOrder())
                .title(reqDto.getTitle())
                .isAuto(reqDto.getIsAuto())
                .status(reqDto.getStatus())
                .build();
        todoItemRepository.save(todoItem);
        return true;

    }
    //todoGroup을 생성 (6)
    public Boolean createTodoGroup(TodoGroupReqDto reqDto) {
        User user = userRepository.findById(reqDto.getUserId()).orElse(null);

        TodoGroup todoGroup = TodoGroup.builder()
                .user(user)
                .title(reqDto.getTitle())
                .description(reqDto.getDescription())
                .build();

        todoGroupRepository.save(todoGroup);
        return true;
    }


    // 삭제
    // todolist 삭제
    public void deleteTodoList(Long id) {
        todoListRepository.deleteById(id);
    }

    public void deleteTodoGroup(Long id) {
        todoGroupRepository.deleteById(id);
    }

    // 업데이트

    public TodoItemResDto updateStatus(Long id) {
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을수 없어용"));
        todoItem.setStatus("old");
        todoItemRepository.save(todoItem);
        return TodoItemResDto.of(todoItem);
    }

    public TodoListResDto updateListCheck(Long id) {
        TodoList todoList = todoListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을수 없어용"));
        if(todoList.getCheckedTime() == null){
            todoList.setCheckedTime(LocalDateTime.now());
        } else {
            todoList.setCheckedTime(null);
        }

        todoListRepository.save(todoList);

        return new TodoListResDto(
                todoList.getUser().getId(),
                todoList.getTodoItem() != null ? todoList.getTodoItem().getId() : null,
                todoList.getDate().toString(),
                todoList.getListOrder(),
                todoList.getTitle()
        );
    }
    // list의 순서랑 , 제목바꾸는거
    public TodoListResDto updateList(Long id, TodoListReqDto reqDto) {
        TodoList todoList = todoListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을수 없어용"));

        todoList.setListOrder(reqDto.getListOrder());
        todoList.setTitle(reqDto.getTitle());
        todoListRepository.save(todoList);

        return new TodoListResDto(
                todoList.getUser().getId(),
                todoList.getTodoItem() != null ? todoList.getTodoItem().getId() : null,
                todoList.getDate().toString(),
                todoList.getListOrder(),
                todoList.getTitle()
        );
    }


    public TodoItemResDto updateItem(Long id, TodoItemReqDto reqDto) {
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없엉"));
        User user = userRepository.findById(reqDto.getUserId()).orElse(null);
        TodoGroup todoGroup = todoGroupRepository.findById(reqDto.getGroupId()).orElse(null);

        todoItem.setStatus("old");
        todoItemRepository.save(todoItem);

        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setTitle(reqDto.getTitle());
        newTodoItem.setDay(reqDto.getDay());
        newTodoItem.setItemOrder(reqDto.getItemOrder());
        newTodoItem.setStatus("new");
        newTodoItem.setAuto(reqDto.getIsAuto());
        newTodoItem.setUser(user);
        newTodoItem.setTodoGroup(todoGroup);

        todoItemRepository.save(newTodoItem);  // 새로운 TodoItem을 저장

        return TodoItemResDto.of(newTodoItem);
    }

    // 그룹 정보 변경
    public TodoGroupResDto updateGroup(Long id, TodoGroupReqDto reqDto) {
        TodoGroup todoGroup = todoGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없엉"));

        User user = userRepository.findById(reqDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없성"));

        todoGroup.setTitle(reqDto.getTitle());
        todoGroup.setDescription(reqDto.getDescription());
        todoGroup.setUser(user);  // user 설정

        todoGroupRepository.save(todoGroup);

        return TodoGroupResDto.oft(todoGroup);
    }





}