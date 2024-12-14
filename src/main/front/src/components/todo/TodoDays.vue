<template>
  <div>
    <!-- 요일 선택 셀렉트 박스 -->
    <div class="select-container">
      <label for="days">요일 선택:</label>
      <select v-model="item.days" id="days" class="days-select">
        <option value="월">월요일</option>
        <option value="화">화요일</option>
        <option value="수">수요일</option>
        <option value="목">목요일</option>
        <option value="금">금요일</option>
        <option value="토">토요일</option>
        <option value="일">일요일</option>
      </select>
    </div>
    
    <!-- 선택된 요일에 해당하는 Todo 목록 출력 -->
    <div v-if="todos && todos.length > 0">
      <h3>{{ item.days }}요일의 Todo 리스트</h3>
      <div class="todo-container">
        <ul>
          <li v-for="todo in todos" :key="todo.id" class="todo-item">
            <div class="todo-title">{{ todo.groupTitle }} -> {{ todo.title }}</div>
          </li>
        </ul>
      </div>
    </div>
    
    <!-- 데이터가 없을 경우 메시지 -->
    <div v-else>
      <p>선택된 요일에 해당하는 Todo가 없습니다.</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      item: {
        days: "", // 선택된 요일
      },
      todos: [], // Todo 목록
    };
  },
  watch: {
    "item.days": function (newDay) {
      if (newDay) {
        this.fetchTodosByDay(newDay); // 요일이 변경될 때마다 fetch
      }
    },
  },
  mounted() {
    if (this.item.days) {
      this.fetchTodosByDay(this.item.days); // 처음에 기본 값이 있을 경우 fetch
    }
  },
  methods: {
    async fetchTodosByDay(day) {
      const userId = 5; // 사용자 ID
      const url = `/todo/item/${userId}/${day}`; // API URL
      try {
        const { data } = await this.$axios.get(url); // axios로 API 호출
        this.todos = data || []; // 받은 데이터가 없을 경우 빈 배열로 초기화
      } catch (error) {
        console.error("Todos를 가져오는 데 실패했습니다.", error);
        this.todos = []; // 에러 발생 시 빈 배열로 설정
      }
    },
  },
};
</script>

<style scoped>
.select-container {
  margin-bottom: 20px;
  font-size: 18px;
}

.days-select {
  padding: 8px 12px;
  font-size: 16px;
  margin-top: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
  width: 100%;
  max-width: 200px;
}

.todo-container {
  margin-top: 20px;
}

.todo-item {
  display: flex;
  margin: 10px 0;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 8px;
  position: relative;
  transition: background-color 0.3s ease;
}

.todo-item:hover {
  background-color: #faf7a3;
}

.todo-title {
  font-size: 18px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: start;
}

.todo-item .actions {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  display: none;
}

.todo-item:hover .actions {
  display: block;
}

.delete-btn {
  margin-left: 10px;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #f0f0f0;
  transition: background-color 0.3s;
}

.delete-btn:hover {
  background-color: #f44336;
  color: white;
}
</style>
