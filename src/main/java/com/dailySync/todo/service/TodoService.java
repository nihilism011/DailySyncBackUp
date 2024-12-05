package com.dailySync.todo.service;//package com.dailySync.todo.service;


import com.dailySync.test.dto.UserTestResDto;
import com.dailySync.test.entities.UserTest;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    final private UserRepository userRepository;
    final private TodoGroupRepository todoGroupRepository;
    final private TodoItemRepository todoItemRepository;
    final private TodoListRepository todoListRepository;

    // 리스트 조회

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
            .orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다"));

    User user = userRepository.findById(reqDto.getUserId())
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

    todoGroup.setTitle(reqDto.getTitle());
    todoGroup.setDescription(reqDto.getDescription());
    todoGroup.setUser(user);  // user 설정

    todoGroupRepository.save(todoGroup);

    return TodoGroupResDto.oft(todoGroup);
}


//    // todoList 생성하고 , 우선순위(4), 날짜 설정
//
//    public TodoListResDto createTodoList(TodoListReqDto reqDto) {
//        // 고정된 날짜 설정
//        LocalDate fixedDate = LocalDate.now();
//
//        // title을 받아옴
//        String title = reqDto.getTitle();
//
//        // TodoItem을 조회할 변수
//        TodoItem todoItem = null;
//
//        // itemId가 있을 경우, 해당 TodoItem을 DB에서 찾음
//        if (reqDto.getItemId() != null) {
//            todoItem = todoItemRepository.findById(reqDto.getItemId())
//                    .orElseThrow(() -> new IllegalArgumentException("itemId 없음"));
//        }
//
//        // itemId가 없을 경우, title을 반드시 입력하도록 체크
//        if (todoItem == null && (title == null || title.trim().isEmpty())) {
//            throw new IllegalArgumentException("itemId가 없을 경우 title을 입력해야 합니다.");
//        }
//
//        // listOrder 결정: itemId가 있으면 해당 TodoItem의 itemOrder, 없으면 4
//        Integer listOrder = (todoItem != null) ? todoItem.getItemOrder() : 4;
//
//        // User 정보 가져오기
//        User user = userRepository.findById(reqDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
//
//        // TodoList 객체 생성
//        TodoList todoList = TodoList.builder()
//                .user(user)
//                .todoItem(todoItem)
//                .date(fixedDate) // 오늘 날짜 고정
//                .listOrder(listOrder)
//                .title(title) // 받은 title
//                .build();
//
//        // TodoList를 데이터베이스에 저장
//        todoList = todoListRepository.save(todoList);
//
//        // 저장된 todoList 객체를 DTO로 변환하여 반환
//        return new TodoListResDto(
//                todoList.getUser().getId(),
//                todoList.getTodoItem() != null ? todoList.getTodoItem().getId() : null,
//                todoList.getDate().toString(),
//                todoList.getListOrder(),
//                todoList.getTitle()
//        );
//    }




}

