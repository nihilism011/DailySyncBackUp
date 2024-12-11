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


    public void TodoLoginAutoListCreate(Long userId) {
        // 1. 해당 유저를 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // 2. 유저의 마지막 로그인 날짜를 현재 로그인 날짜로 업데이트
        LocalDate lastLogin = user.getLastLogin() != null ? user.getLastLogin().toLocalDate() : null;
        LocalDate today = LocalDate.now();

        // 3. 만약 마지막 로그인 날짜와 오늘 날짜가 다르면, TodoList 생성
        if (lastLogin == null || !lastLogin.isEqual(today)) {
            // 날짜가 다르면 TodoList를 자동 생성
            createTodoListsForUser(user, today);

            // 4. 마지막 로그인 날짜를 오늘 날짜로 업데이트
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    // 유저에 맞춰 TodoList를 생성하는 메서드
    private void createTodoListsForUser(User user, LocalDate today) {

        List<TodoItem> todoItems = todoItemRepository.findByUserIdAndStatusAndIsAuto(user.getId(), "new", true);

        //각 TodoItem에 대해 day 배열을 확인하고, 해당 요일에 맞는 TodoList 생성
        for (TodoItem todoItem : todoItems) {
            for (String day : todoItem.getDay()) {
                // 4. 오늘 날짜를 기준으로 해당 요일에 맞는 날짜를 계산
                LocalDate targetDate = getNextDateForDay(today, day);

                if (targetDate != null && targetDate.isEqual(today)) {  // 오늘 날짜와 매칭되는 경우만 생성
                    // 이미 해당 날짜에 TodoList가 생성되었는지 확인
                    if (!todoListRepository.existsByUserIdAndDateAndTodoItemId(user.getId(), targetDate, todoItem.getId())) {
                        // TodoList 생성
                        TodoList todoList = TodoList.builder()
                                .user(user)
                                .todoItem(todoItem)
                                .date(targetDate)
                                .title(todoItem.getTitle())  // 제목은 TodoItem에서 가져옴
                                .listOrder(todoItem.getItemOrder())  // 우선순위(기본값)
                                .build();

                        todoListRepository.save(todoList);
                    }
                }
            }
        }
    }

    // 주어진 요일에 맞는 날짜를 구하는 메서드
    private LocalDate getNextDateForDay(LocalDate today, String dayOfWeek) {
        // 요일을 숫자(0 = 일요일, 1 = 월요일, ..., 6 = 토요일)로 변환
        int targetDay = getDayOfWeek(dayOfWeek);

        if (targetDay == -1) {
            return null;  // 잘못된 요일이면 null 반환
        }

        // 오늘의 요일
        int todayDay = today.getDayOfWeek().getValue() % 7;  // 0 = 월요일, 6 = 일요일

        // 목표 요일까지 며칠 남았는지 계산
        int daysUntilTarget = (targetDay - todayDay + 7) % 7;

        // 해당 날짜를 반환
        return today.plusDays(daysUntilTarget);
    }

    // 요일 문자열을 숫자로 변환
    private int getDayOfWeek(String day) {
        switch (day) {
            case "일": return 0;
            case "월": return 1;
            case "화": return 2;
            case "수": return 3;
            case "목": return 4;
            case "금": return 5;
            case "토": return 6;
            default: return -1;
        }
    }


    // 날짜 받아와서 총개수 , null인개수 반환
    public TodoCountResponseDto getTodoCount(Long userId, LocalDate date){

        long nullCount = todoListRepository.countByUserIdAndDateAndCheckedTimeIsNull(userId, null);
        long allCount = todoListRepository.countByUserIdAndDate(userId, date);

        return new TodoCountResponseDto(nullCount, allCount);

    }
    //userId에 해당하는 todoGroup을 조회 (5)
    public List<TodoGroupResDto> getTodoGroup(Long userId) {
        List<TodoGroup> todoGroups = todoGroupRepository.findByUserIdAndStatusOrderByCreatedAtAsc(userId, "new");
        return todoGroups.stream()
                .map(TodoGroupResDto::of)
                .collect(Collectors.toList());
    }
    //userId, groupId 에 해당하는 todoItem 조회
    public List<TodoItemResDto> getTodoItem(Long userId, Long groupId) {
        List<TodoItem> todoItems
                = todoItemRepository.findByUserIdAndTodoGroupIdAndStatusOrderByCreatedAtAsc(userId, groupId,"new");
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
    public Boolean createTodoList(Long userId,TodoListReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

        int listOrder = reqDto.getListOrder() != null ? reqDto.getListOrder() : 4; // 고정값

        TodoList todoList = TodoList.builder()
                .user(user)
                .date(LocalDate.now())
                .checkedTime(null)
                .title(reqDto.getTitle())
                .listOrder(listOrder)
                .build();

        todoListRepository.save(todoList);
        return true;
    }


    //todoItem을 생성 (7)
    public Boolean createTodoItem(TodoItemReqDto reqDto) {

        if (reqDto.getGroupId() == null) {
            // userId 또는 groupId가 null인 경우 예외를 던짐
            throw new IllegalArgumentException("그룹아이디들 제대로 안넘어옴 ");
        }
        if (reqDto.getUserId() == null ) {
            // userId 또는 groupId가 null인 경우 예외를 던짐
            throw new IllegalArgumentException("유ㅜ저아이디들 제대로 안넘어옴 ");
        }

        User user = userRepository.findById(reqDto.getUserId()).orElse(null);
        TodoGroup todoGroup = todoGroupRepository.findById(reqDto.getGroupId()).orElse(null);

        TodoItem todoItem = TodoItem.builder()
                .user(user)
                .todoGroup(todoGroup)
                .day(reqDto.getDay())  // List<String> 형태로 받아서 저장
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
                .status("new")
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

    public TodoGroupResDto updateGroupStatus(Long id) {
        TodoGroup todoGroup = todoGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그룹을 찾을수 없어용"));
        todoGroup.setStatus("old");
        todoGroupRepository.save(todoGroup);

        for (TodoItem todoItem : todoGroup.getTodoItems()) {
            todoItem.setStatus("old");
            todoItemRepository.save(todoItem); // 상태 변경된 아이템을 저장
        }

        return TodoGroupResDto.of(todoGroup);
    }

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
        newTodoItem.setIsAuto(reqDto.getIsAuto());
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