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

    // 리스트 불러오기

    public List<TodoGroupResDto> getTodoGroup(Long userId) {
        // 여러 개의 TodoGroup을 가져옴
        List<TodoGroup> todoGroups = todoGroupRepository.findByUserIdOrderByCreatedAtAsc(userId);

        // TodoGroup을 TodoGroupResDto로 변환하고 리스트로 반환
        return todoGroups.stream()
                .map(TodoGroupResDto::of)  // 각 TodoGroup을 TodoGroupResDto로 변환
                .collect(Collectors.toList());  // 결과를 List로 반환
    }

    public List<TodoItemResDto> getTodoItem(Long userId, Long groupId) {
        List<TodoItem> todoItems
                = todoItemRepository.findByUserIdAndTodoGroupIdOrderByCreatedAtAsc(userId, groupId);
        return todoItems.stream().map(TodoItemResDto::of).collect(Collectors.toList());

    }

    //생성

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
                .build();
        todoItemRepository.save(todoItem);
        return true;

    }

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



    //------------------------------------------------------------------------
    // 로그인 후 날짜 기준으로 해당 날짜의 todoList를 반환
    public List<TodoListResDto> getTodoList(Long userId, LocalDate date) {
        List<TodoList> todoLists = todoListRepository.findByUserIdAndDateOrderByListOrderAsc(userId, date);
        return todoLists.stream()
                .map(todoList -> TodoListResDto.of(todoList.getId(), todoList.getTodoItem().getId(), todoList.getDate().toString(), todoList.getListOrder()))
                .collect(Collectors.toList());
    }
    // todolist 삭제
    public void deleteTodoList(Long id) {
        todoListRepository.deleteById(id);
    }
    //todolist  수정
    public TodoListResDto updateTodoList(Long id, TodoListReqDto reqDto) {
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("TodoList not found"));
        todoList.setTitle(reqDto.getTitle());
        todoList.setListOrder(reqDto.getListOrder());
        todoListRepository.save(todoList);
        return TodoListResDto.of(todoList.getId(), todoList.getDate().toString(), todoList.getListOrder());
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

