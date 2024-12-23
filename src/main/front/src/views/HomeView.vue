<template>
  <div class="dashboard-container">
    <div class="dashboard-content">
      <div class="dashboard-header">
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">날짜 선택</p>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input type="date" v-model="selectedDate" />
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">대시보드 선택</p>
          </div>
          <div class="bot-box">
            <div class="ip-chk-txt">
              <input id="todo" type="checkbox" v-model="isTodoView" @change="fetchView" />
              <label for="todo">Todo</label>
              <input id="schedule" type="checkbox" v-model="isScheduleView" @change="fetchView" />
              <label for="schedule">일정</label>
              <input id="account" type="checkbox" v-model="isAccountView" @change="fetchView" />
              <label for="account">가계부</label>
              <input id="meal" type="checkbox" v-model="isMealView" @change="fetchView" />
              <label for="meal">식단</label>
            </div>
          </div>
        </div>
      </div>
      <div class="dashboard-sections">
        <div v-if="isTodoView" class="dashboard-item todo">
          <TodoDashboard :selectedDate="selectedDate" />
        </div>
        <div v-if="isScheduleView" class="dashboard-item schedule">
          <ScheduleDashboard :selectedDate="selectedDate" />
        </div>
        <div v-if="isAccountView" class="dashboard-item finance">
          <AccountDashboard :selectedDate="selectedDate" />
        </div>
        <div v-if="isMealView" class="dashboard-item diet">
          <MealDashboard :selectedDate="selectedDate" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MealDashboard from '@/components/dashboard/MealDashboard.vue'
import TodoDashboard from '@/components/dashboard/TodoDashboard.vue'
import AccountDashboard from '@/components/dashboard/AccountDashboard.vue'
import ScheduleDashboard from '@/components/dashboard/ScheduleDashboard.vue'
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
.dashboard {
  &-container {
    width: 100%;
    padding: 30px;
    height: 100%;
  }
  &-content {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    gap: 20px 0;
  }
  &-header {
    display: flex;
    gap: 0 20px;
    .ip-list {
      display: flex;
      flex-direction: column;
    }
    & > .ip-list {
      margin-top: 0;
    }
    .bot-box {
      flex-grow: 1;
      display: flex;
      align-items: center;
    }
  }
  &-sections {
    display: flex;
    gap: 0 20px;
    flex-grow: 1;
  }
  &-item {
    width: calc(25% - 5px);
    padding: 16px 24px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3);
  }
}
</style>
