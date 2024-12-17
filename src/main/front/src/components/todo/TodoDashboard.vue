<template>
  <div class="popup-cont">
    <RouterLink to="/todo" style="text-align: right;">Todo 이동하기 >> </RouterLink>
    <div v-if="todayCompletionRate !== null" class="today-completion-rate">
      <h2>{{ isToday ? '오늘의 진행률' : `${selectedDate}의 진행률` }}</h2>
      <div class="completion-rate">{{ Math.round(todayCompletionRate) }}%</div>
    </div>

    <div class="todo-items">
      <div class="item" v-for="(item, index) in items" :key="index">
        <div class="item-info">
          <span class="item-tit">{{ item.title }}</span>
        </div>
        <template v-if="item.status === 'new'">
          <span class="item-date">{{ formatTimeWithoutSeconds(item.checkedTime) }}</span>
        </template>
        <template v-else>
          <span class="item-date">
            <div> 생성일 : {{ formatDateTime(item.createdAt) }}</div>
            <div> 완료일 : {{ formatDateTime(item.checkedTime) }}</div>
          </span>
        </template>
        <input type="checkbox"/>
      </div>
    </div>
  </div>
</template>

<script>
import { RouterLink } from 'vue-router'
export default {
  components: { RouterLink },
  data() {
    return {
      items: [],
      todayCompletionRate: null, 
      isToday: false, 
    };
  },
  props: {
    selectedDate: {
      type: String,
      default: '',
    },
  },
  watch: {
    selectedDate(newVal) {
      if (newVal) {
        this.fnMySelectList();
        this.fnDayList(newVal); 
      }
    },
  },
  methods: {
    // Todo 페이지로 이동하는 메소드
    navigateToTodo() {
      this.$router.push({ name: 'TodoPage' });
    },

    async fnDayList(selectedDate) {
      const today = new Date();
      const year = selectedDate.split('-')[0];
      const month = selectedDate.split('-')[1];
      
      this.isToday = selectedDate === today.toISOString().split('T')[0]; 

      const daily = await this.$axios.get(`todo/list/${year}/${month}`);
      if (daily.status) {
        const todoCounts = daily.data.todoCounts;
        
        const todayData = todoCounts.find(item => item.date === selectedDate);
        if (todayData) {
          this.todayCompletionRate = todayData.completionRate;
        } else {
          this.todayCompletionRate = 0;
        }
      } 
    },

    async fnMySelectList() {
      if (!this.selectedDate) return;
      const url = `todo/list/${this.selectedDate}`;
      try {
        const { data } = await this.$axios.get(url);
        this.items = data;
      } catch (error) {
        console.error("데이터 가져오기 실패:", error);
      }
    },

    formatTimeWithoutSeconds(time) {
      if (!time) return "활동 안함";
      const date = new Date(time);
      return `${date.getHours()}시 ${date.getMinutes()}분`;
    },

    formatDateTime(dateTime) {
      if (!dateTime) return "활동 안함";
      const date = new Date(dateTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      return `${year}-${month}-${day} ${hours}시${minutes}분`;
    },
  },
  mounted() {
    if (this.selectedDate) {
      this.fnMySelectList();
      this.fnDayList(this.selectedDate);
    }
  },
};
</script>


<style lang="scss" scoped>
.todo-items {
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  border-top: 1px solid var(--color-contrastyC8);
  border-bottom: 1px solid var(--color-contrastyC8);
  overflow-y: auto;
  height: 500px;
  .item {
    display: flex;
    width: 98%;
    border-bottom: 1px solid var(--color-contrastyC8);
    padding: 10px 10px;
    position: relative;
    min-height: 50px;
    &-info {
      display: flex;
      align-items: center;
      width: calc(140% - 220px);
      .item-tit {
        font-size: 15px;
        font-weight: bold;
      }
      .item-group-tit {
        font-size: 15px;
        color: var(--color-contrastyA);
        margin-left: 10px;
      }
    }
    &-date {
      font-size: 14px;
      color: #333; /* Darker color */
      position: absolute;
      right: 0; top: 10px;
    }
    &:last-child {
      border-bottom: 0;
    }
  }
}

.today-completion-rate {
  margin-top: 30px;
  text-align: center;
  
  h2 {
    font-size: 24px;
    margin-bottom: 10px;
  }

  .completion-rate {
    font-size: 48px;
    font-weight: bold;
    color: #4CAF50 !important; /* Green color for progress with !important */
  }
}

.completed-time, .item-date {
  font-size: 30px;
  font-weight: bold;
  color: #333; /* Darker color for better visibility */
}

.completed-time {
  margin-top: 5px;
}

.inactive {
  font-size: 30px;
  font-weight: bold;
  color: #757575; /* Changed to gray color for inactivity */
}
</style>

