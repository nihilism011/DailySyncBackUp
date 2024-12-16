<template>
  <div class="left">
    <TodoCalendar :dailyList="dailyList" @fnDayList="fnDayList" />
    <TodoMemo/>
  </div>
  <div class="right todo-type">
    <div class="todo-wrap">
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
import TodoCalendar from '@/components/todo/TodoCalender.vue';
import TodoMemo from '@/components/todo/TodoMemo.vue';

export default {
  components: {
    TodoGroup,
    TodoItem,
    TodoList,
    TodoCalendar,
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
    async todoauto() {
      const url = `todo/autoTest`;
      await this.$axios.post(url);
    },
    async fnDayList(inputDay) {
      console.log("가져오는 날짜" + inputDay);
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];
      console.log("year" + year + "month" + month);
      const daily = await this.$axios.get(`todo/list/${year}/${month}`);
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
    this.todoauto();
    this.fnDayList();
  },
};
</script>

<style scoped>
.todo-wrap {
  display: flex;
  height: 825px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
  padding: 20px;
  gap: 0 20px;
  overflow: hidden;
  > div {
    overflow-y: auto;
    padding: 20px;
  }
}
.right-left {
  width: 40%;
}

.right-middle {
  width: 25%;
}

.right-right {
  width: 35%;
}
</style>
