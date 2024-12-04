package com.dailySync.todo.service;//package com.dailySync.todo.service;
//
//
//import com.dailySync.todo.dto.TodoGroupReqDto;
//import com.dailySync.todo.dto.TodoItemReqDto;
//import com.dailySync.todo.dto.TodoListReqDto;
//import com.dailySync.todo.entities.TodoGroup;
//import com.dailySync.todo.entities.TodoItem;
//import com.dailySync.todo.entities.TodoList;
//import com.dailySync.todo.repository.TodoGroupRepository;
//import com.dailySync.todo.repository.TodoItemRepository;
//import com.dailySync.todo.repository.TodoListRepository;
//import com.dailySync.user.entities.User;
//import com.dailySync.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class TodoService {
//    final private UserRepository userRepository;
//    final private TodoGroupRepository todoGroupRepository;
//    final private TodoItemRepository todoItemRepository;
//    final private TodoListRepository todoListRepository;
//
//
//    public List<TodoGroup> getTodoGroupsByUserId(Long userId){
//        return todoGroupRepository.findByUserId(userId);
//    }
//
//    public List<TodoItem> getTodoItemsByUserIdAndGroupId(Long userId, Long groupId){
//        return todoItemRepository.findByUserIdAndTodoGroupId(userId, groupId);
//    }
//
//    public List<TodoList> getTodoListsByUserIdAndToday(Long userId) {
//        return todoListRepository.findByUserIdAndDate(userId, LocalDate.now());
//    }
//    /** todo리스트 생성 */
//    public TodoList createTodoList(Long userId, Long itemId, TodoListReqDto reqDto) {
//        User user = userRepository.findById(userId).orElse(null);
//        TodoItem todoItem = todoItemRepository.findById(itemId).orElse(null);
//        TodoList todoList = TodoList.builder()
//                .date(reqDto.getDate())
//                .checkedTime(reqDto.getCheckedTime())
//                .listOrder(reqDto.getListOrder())
//                .user(user)
//                .todoItem(todoItem)
//                .build();
//        return todoListRepository.save(todoList);
//    }
//    /** todo그룹 생성 */
//    public TodoGroup createTodoGroup(Long userId, TodoGroupReqDto reqDto) {
//        User user = userRepository.findById(userId).orElse(null);
//            TodoGroup todoGroup = TodoGroup.builder()
//                    .title(reqDto.getTitle())
//                    .description(reqDto.getDescription())
//                    .isAuto(reqDto.isAuto())
//                    .user(user)
//                    .build();
//            return todoGroupRepository.save(todoGroup);
//    }
//    /** todo아이템 생성 */
//    public TodoItem createTodoItem(Long userId, Long groupId, TodoItemReqDto reqDto) {
//        User user = userRepository.findById(userId).orElse(null);
//        TodoGroup todoGroup = todoGroupRepository.findById(groupId).orElse(null);
//            TodoItem todoItem = TodoItem.builder()
//                    .day(reqDto.getDay())
//                    .itemOrder(reqDto.getItemOrder())
//                    .title(reqDto.getTitle())
//                    .status(reqDto.getStatus())
//                    .user(user)
//                    .todoGroup(todoGroup)
//                    .build();
//        return todoItemRepository.save(todoItem);
//
//    }
//    /** todoItem 삭제 */
//    public void deleteItemById(long id){
//        todoItemRepository.deleteById(id);
//    }
//    /** todoList 삭제 */
//    public void deleteListById(long id){
//        todoListRepository.deleteById(id);
//    }
//    /** todoGroup 삭제 */
//    public void deleteGroupById(long id){
//        todoGroupRepository.deleteById(id);
//    }
//
//}
