package com.dailySync.todo.service;//package com.dailySync.todo.service;

import com.dailySync.todo.dto.*;
import com.dailySync.todo.entities.TodoGroup;
import com.dailySync.todo.entities.TodoItem;
import com.dailySync.todo.entities.TodoList;
import com.dailySync.todo.entities.TodoMemo;
import com.dailySync.todo.repository.TodoGroupRepository;
import com.dailySync.todo.repository.TodoItemRepository;
import com.dailySync.todo.repository.TodoListRepository;
import com.dailySync.todo.repository.TodoMemoRepository;
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
    final private TodoMemoRepository todoMemoRepository;


    public TodoListResDto getUserTodolList(Long userId, Integer year, Integer month) {
        // 쿼리 실행: 날짜별 총 개수와 체크된 개수 가져오기
        List<Object[]> result = todoListRepository.findTodoCountsByDate(userId, year, month);

        // 결과를 DTO로 변환
        List<TodoCountResponseDto> todoCountList = result.stream().map(obj -> {
            LocalDate date = (LocalDate) obj[0];  // 첫 번째 값: 날짜
            Long totalCount = (Long) obj[1];  // 두 번째 값: 총 개수
            Long checkedCount = (Long) obj[2];  // 세 번째 값: 체크된 개수

            // 완료율 계산
            double completionRate = totalCount == 0 ? 0 : (double) checkedCount / totalCount * 100;

            return TodoCountResponseDto.builder()
                    .date(date)
                    .CNT(totalCount)  // 총 개수
                    .checkedCnt(checkedCount)  // 체크된 개수
                    .completionRate(completionRate)  // 완료율 추가
                    .build();
        }).collect(Collectors.toList());

        // 완료율을 기반으로 title 설정
        double totalCnt = todoCountList.stream().mapToLong(TodoCountResponseDto::getCNT).sum();
        double checkedCnt = todoCountList.stream().mapToLong(TodoCountResponseDto::getCheckedCnt).sum();
        double totalCompletionRate = totalCnt == 0 ? 0 : (checkedCnt / totalCnt) * 100;

        // 완료율을 포함한 title
        String titleWithCompletionRate = String.format("Todo List (%.2f%% 완료)", totalCompletionRate);

        // TodoListResDto에 결과 담기
        return TodoListResDto.builder()
                .title(titleWithCompletionRate)  // 완료율이 포함된 title
                .todoCounts(todoCountList)  // 날짜별 통계 포함
                .build();
    }

    public List<TodoItemResDto> getTodoItemDay(Long userId, String day) {
        // 해당 userId와 day를 가진 TodoItem 조회
        List<TodoItem> todoItems = todoItemRepository.findByUserIdAndDay(userId, day);

        // TodoItem들을 TodoItemResDto로 변환하여 반환
        return todoItems.stream()
                .map(TodoItemResDto::of3)
                .collect(Collectors.toList());
    }


    // 달력 일 가져오기
    public List<TodoListResDto> getTodoList(Long userId, LocalDate date) {
        List<TodoList> todoLists = todoListRepository.findByUserIdAndDateOrderByListOrderAsc(userId, date);
        return todoLists.stream()
                .map(TodoListResDto::of)
                .collect(Collectors.toList());
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
        List<TodoList> todoLists = todoListRepository.findByUserIdAndDateOrderByListOrderAndGroupId(userId, today);
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
                .status("new")
                .build();

        todoListRepository.save(todoList);
        return true;
    }

    //todoMemo을 생성
    public Boolean createTodoMemo(Long userId,TodoMemoReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));
        TodoMemo todomemo = TodoMemo.builder()
                .user(user)
                .contents(reqDto.getContents())
                .build();
       todoMemoRepository.save(todomemo);
       return true;
    }
    //userId해당하는는 todoMemo 조회
    public TodoMemoResDto getTodoMemo(Long userId) {
        TodoMemo todoMemo = todoMemoRepository.findByUserId(userId).orElse(null);
        return TodoMemoResDto.of(todoMemo);

    }
    //todoItem을 생성 (7)
    public Boolean createTodoItem(Long userId, TodoItemReqDto reqDto) {

        if (reqDto.getGroupId() == null) {
            throw new IllegalArgumentException("그룹아이디가 제대로 넘어오지 않았어.");
        }
        if (userId == null) {
            throw new IllegalArgumentException("유저아이디가 제대로 넘어오지 안항ㅆ어.");
        }

        User user = userRepository.findById(userId).orElse(null);
        TodoGroup todoGroup = todoGroupRepository.findById(reqDto.getGroupId()).orElse(null);

        // TodoItem 생성
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

        // 오늘 날짜와 매칭되는 경우 TodoList 생성 (todayCreate가 true일 때)
        if (reqDto.getTodayCreate() != null && reqDto.getTodayCreate()) {
            if (todoItem.getIsAuto() != null && todoItem.getIsAuto()) {
                LocalDate today = LocalDate.now();
                String todayDayOfWeek = getDayOfWeekItem(today);

                if (reqDto.getDay().contains(todayDayOfWeek)) {
                    boolean exists = todoListRepository.existsByUserIdAndDateAndTodoItemId(userId, today, todoItem.getId());
                    if (!exists) {
                        TodoList todoList = TodoList.builder()
                                .user(user)
                                .todoItem(todoItem)
                                .date(today)
                                .title(reqDto.getTitle())
                                .listOrder(reqDto.getItemOrder())
                                .status("new")
                                .build();

                        todoListRepository.save(todoList);
                    }
                }
            }
        }

        return true;
    }


    //todoGroup을 생성 (6)
    public Boolean createTodoGroup(Long userId, TodoGroupReqDto reqDto) {
        User user = userRepository.findById(userId).orElse(null);

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

    public void deleteTodoMemo(Long id) {
        todoMemoRepository.deleteById(id);
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


    public TodoItemResDto updateItem(Long userId, Long id, TodoItemReqDto reqDto) {
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없엉"));
        User user = userRepository.findById(userId).orElse(null);
        TodoGroup todoGroup = todoGroupRepository.findById(reqDto.getGroupId()).orElse(null);

        todoItem.setStatus("old");
        todoItemRepository.save(todoItem);

        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setTitle(reqDto.getTitle()); //
        newTodoItem.setDay(reqDto.getDay()); //
        newTodoItem.setItemOrder(reqDto.getItemOrder()); //
        newTodoItem.setStatus("new");
        newTodoItem.setIsAuto(reqDto.getIsAuto());  //
        newTodoItem.setUser(user);
        newTodoItem.setTodoGroup(todoGroup);

        todoItemRepository.save(newTodoItem);  // 새로운 TodoItem을 저장

        return TodoItemResDto.of(newTodoItem);
    }

    // 그룹 정보 변경
    public TodoGroupResDto updateGroup(Long userId, Long id, TodoGroupReqDto reqDto) {
        TodoGroup todoGroup = todoGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없엉"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없성"));

        todoGroup.setTitle(reqDto.getTitle());
        todoGroup.setDescription(reqDto.getDescription());
        todoGroup.setUser(user);  // user 설정

        todoGroupRepository.save(todoGroup);

        return TodoGroupResDto.oft(todoGroup);
    }

    public TodoMemoResDto getTodoUpdateMemo(Long id, TodoMemoReqDto reqDto) {
        TodoMemo todomemo = todoMemoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("메모을 찾을 수 없엉"));

        todomemo.setContents(reqDto.getContents());
        todoMemoRepository.save(todomemo);
        return TodoMemoResDto.of(todomemo);
    }

    // 리스트 자동생성 -----------(아래)
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

            updateOldTodoLists(userId);
        }
    }

    private void updateOldTodoLists(Long userId) {

        List<TodoList> todoLists = todoListRepository.findByUserIdAndTodoItemIsNullAndCheckedTimeIsNotNull(userId);

        for (TodoList todoList : todoLists) {
            todoList.setStatus("old");
            todoList.setDate(todoList.getCheckedTime().toLocalDate());

            todoListRepository.save(todoList);
        }
    }



    private void createTodoListsForUser(User user, LocalDate today) {

        List<TodoItem> todoItems = todoItemRepository.findByUserIdAndStatusAndIsAuto(user.getId(), "new", true);

        for (TodoItem todoItem : todoItems) {
            for (String day : todoItem.getDay()) {
                LocalDate targetDate = getNextDateForDay(today, day);
                if (targetDate != null && targetDate.isEqual(today)) {  // 오늘 날짜와 매칭되는 경우만 생성
                    if (!todoListRepository.existsByUserIdAndDateAndTodoItemId(user.getId(), targetDate, todoItem.getId())) {

                        TodoList todoList = TodoList.builder()
                                .user(user)
                                .todoItem(todoItem)
                                .date(targetDate)
                                .title(todoItem.getTitle())  // 제목은 TodoItem에서 가져옴
                                .listOrder(todoItem.getItemOrder())  // 우선순위(기본값)
                                .status("new")
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
    private String getDayOfWeekItem(LocalDate date) {
        switch (date.getDayOfWeek()) {
            case SUNDAY:
                return "일";
            case MONDAY:
                return "월";
            case TUESDAY:
                return "화";
            case WEDNESDAY:
                return "수";
            case THURSDAY:
                return "목";
            case FRIDAY:
                return "금";
            case SATURDAY:
                return "토";
            default:
                throw new IllegalArgumentException("Invalid day of week");
        }
    }

}