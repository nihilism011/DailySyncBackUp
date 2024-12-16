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
      </div>

      <div class="dashboard-sections">
        <div class="dashboard-item schedule">
          <h3>스케줄</h3>
        </div>

        <div class="dashboard-item todo">
          <TodoDashboard :selectedDate="selectedDate" />
        </div>

        <div class="dashboard-item diet">
          <h3>식단</h3>
        </div>

        <div class="dashboard-item finance">
          <h3>가계부</h3>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import TodoDashboard from '@/components/todo/TodoDashboard.vue';
export default {
  components: {
    TodoDashboard,
  },
  data() {
    return {
      selectedDate: new Date().toISOString().split('T')[0],
    };
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
  margin-top:-20px;
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
