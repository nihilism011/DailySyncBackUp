<template>
  <div class="dashboard-container">
    <!-- 왼쪽 사이드바 (유저 프로필 및 네비게이션) -->
    <div class="user-sidebar">
      <div class="user-profile">
        <h2 class="user-name">사용자 이름</h2>
      </div>
      <div class="user-nav">
        <ul>
          <li><a href="#">프로필</a></li>
          <li><a href="#">설정</a></li>
          <li><a href="#">로그아웃</a></li>
        </ul>
      </div>
    </div>

    <div class="dashboard-content">
      <div class="dashboard-header">
        <h1><input type="date" class="date-picker" v-model="selectedDate" /></h1>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">대시보드 선택</p>
          </div>
          <div class="bot-box">
            <div class="ip-chk-txt">
              <input id="schedule" type="checkbox" v-model="isScheduleView" @change="fetchView" />
              <label for="schedule">schedule</label>
              <input id="todo" type="checkbox" v-model="isTodoView" @change="fetchView" />
              <label for="todo">todo</label>
              <input id="meal" type="checkbox" v-model="isMealView" @change="fetchView" />
              <label for="meal">meal</label>
              <input id="account" type="checkbox" v-model="isAccountView" @change="fetchView" />
              <label for="account">account</label>
            </div>
          </div>
        </div>
      </div>

      <div class="dashboard-sections">
        <div v-if="isScheduleView" class="dashboard-item schedule">
          <ScheduleDashboard :selectedDate="selectedDate" />
        </div>

        <div v-if="isTodoView" class="dashboard-item todo">
          <TodoDashboard :selectedDate="selectedDate" />
        </div>

        <div v-if="isMealView" class="dashboard-item diet">
          <MealDashboard :selectedDate="selectedDate" />
        </div>

        <div v-if="isAccountView" class="dashboard-item finance">
          <AccountDashboard :selectedDate="selectedDate" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MealDashboard from '@/components/dashboard/MealDashboard.vue'
import TodoDashboard from '@/components/todo/TodoDashboard.vue'
import AccountDashboard from '@/components/account/layout/AccountDashboard.vue'
import ScheduleDashboard from '@/components/schedule/ScheduleDashboard.vue';
export default {
  components: {
    ScheduleDashboard,
    TodoDashboard,
    MealDashboard,
    AccountDashboard,
  },
  data() {
    return {
      selectedDate: new Date().toISOString().split('T')[0],
      isScheduleView: JSON.parse(localStorage.getItem('scheduleView')) ?? true,
      isTodoView: JSON.parse(localStorage.getItem('todoView')) ?? true,
      isMealView: JSON.parse(localStorage.getItem('mealView')) ?? true,
      isAccountView: JSON.parse(localStorage.getItem('accountView')) ?? true,
    }
  },
  methods: {
    fetchView() {
      localStorage.setItem('scheduleView', JSON.stringify(this.isScheduleView))
      localStorage.setItem('todoView', JSON.stringify(this.isTodoView))
      localStorage.setItem('mealView', JSON.stringify(this.isMealView))
      localStorage.setItem('accountView', JSON.stringify(this.isAccountView))
    },
  },
}
</script>
<style lang="scss" scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
  width: 100%;
}

.user-sidebar {
  width: 10%;
  background-color: #f4f4f4;
  padding: 20px;
  border-right: 1px solid #ddd;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.user-profile {
  text-align: center;
  margin-bottom: 30px;
}

.user-name {
  font-size: 1.2em;
  font-weight: bold;
}

.user-nav ul {
  list-style: none;
  padding: 0;
}

.user-nav li {
  margin: 10px 0;
}

.user-nav a {
  text-decoration: none;
  color: #333;
  font-weight: bold;
}

.dashboard-content {
  width: 90%;
  padding: 40px;
  background-color: #fff;
  overflow-y: auto;
  margin-top: -20px;
}

.dashboard-header h1 {
  font-size: 2em;
  margin-bottom: 20px;
}

.date-picker {
  font-size: 1em;
  padding: 10px;
  margin-top: 10px;
}

.dashboard-sections {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr; /* 4개의 항목을 수평으로 나열 */
  gap: 20px;
  height: calc(80vh - 60px); /* 화면 꽉 차게 높이 설정 */
}

.dashboard-item {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  overflow: auto;
}

.dashboard-item h3 {
  margin-bottom: 15px;
  font-size: 1.2em;
}

@media (max-width: 768px) {
  .dashboard-container {
    flex-direction: column;
  }

  .user-sidebar {
    width: 100%;
    margin-bottom: 20px;
  }

  .dashboard-content {
    width: 100%;
  }

  .dashboard-sections {
    grid-template-columns: 1fr; /* 작은 화면에서는 한 열로 쌓이게 */
    grid-template-rows: auto;
  }
}
</style>
