<template>
  <div class="container">
    <div class="left left-container">
      <Calendar :dailyList="dailyList" @fnDayList="fnDayList" />
      <TodoMemo/>
    </div>
    <div class="right">
      <div class="right-left">
        <TodoList/>
      </div>
      <div class="right-middle">
        <TodoGroup @updateSelectedGroup="selectedGroup = $event" />
      </div>
      <div class="right-right">
        <TodoItem :selectedGroup="selectedGroup" />
      </div>
    </div>
  </div>
</template>

<script>
import TodoGroup from '@/components/todo/TodoGroup.vue';
import TodoItem from '@/components/todo/TodoItem.vue';
import TodoList from '@/components/todo/TodoList.vue';
import Calendar from '@/components/todo/TodoCalender.vue';
import TodoMemo from '@/components/todo/TodoMemo.vue';

export default {
  components: {
    TodoGroup,
    TodoItem,
    TodoList,
    Calendar,
    TodoMemo,
  },
  data() {
    return {
      selectedDate: this.$dayjs().format('YYYY-MM-DD'), // 기본적으로 오늘 날짜
      dailyList: [], 
      selectedGroup: null,
    };
  },
  methods: {
    async test() {
      const url = `todo/autoTest`;
      await this.$axios.post(url);
    },
    async fnDayList(inputDay) {
      console.log("가져오는 날짜" + inputDay);
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];
      const userId = 5; 
      console.log("year" + year + "month" + month);
      const daily = await this.$axios.get(`todo/list/${userId}/${year}/${month}`);
      if (daily.status) {
        this.dailyList = daily.data.todoCounts.map((item) => ({
          completionRate: Math.round(item.completionRate), 
          title: `${item.cnt} (${Math.round(item.completionRate)}%)`, 
          date: item.date, 
        }))
        console.log("스테이터스 " + daily.status);
      } else {
        console.log(daily.message);
      }
    },
  },
  mounted() {
    this.test();
  },
};
</script>

<style scoped>
.container {
  display: flex;
  width: 100%;
  height: 100vh;
}

.left-container {
  width: 30%;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.right {
  width: 70%;
  display: flex;
}

.right-left {
  width: 100%;
  padding: 20px;
  background-color: lightgray;
}

.right-middle {
  width: 15%;
  padding: 20px;
  background-color: lightgreen;
}

.right-right {
  width: 30%;
  padding: 20px;
  background-color: lightblue;
}
</style>
