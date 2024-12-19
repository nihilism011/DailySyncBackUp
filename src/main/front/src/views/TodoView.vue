<template>
  <div class="left">
    <TodoCalendar :dailyList="dailyList" @fnDayList="fnDayList" />
    <TodoMemo />
  </div>
  <div class="right todo-type">
    <div class="todo-wrap">
      <div class="todo-box">
        <TodoList @update-day-list="handleDayListUpdate" />
      </div>
      <div class="todo-box">
        <div class="first">
          <TodoGroup @updateSelectedGroup="selectedGroup = $event" />
        </div>
        <div class="second">
          <TodoItem :selectedGroup="selectedGroup" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TodoGroup from '@/components/todo/TodoGroup.vue'
import TodoItem from '@/components/todo/TodoItem.vue'
import TodoList from '@/components/todo/TodoList.vue'
import TodoCalendar from '@/components/todo/TodoCalender.vue'
import TodoMemo from '@/components/todo/TodoMemo.vue'

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
    }
  },
  methods: {
    async todoauto() {
      const url = `todo/autoTest`
      await this.$axios.post(url)
    },
    async fnDayList(inputDay) {
      console.log('가져오는 날짜' + inputDay)
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      console.log('year' + year + 'month' + month)
      const daily = await this.$axios.get(`todo/list/${year}/${month}`)
      if (daily.status) {
        this.dailyList = daily.data.todoCounts.map((item) => ({
          completionRate: Math.round(item.completionRate),
          title: `${item.cnt} (${Math.round(item.completionRate)}%)`,
          date: item.date,
        }))
        console.log('스테이터스 ' + daily.status)
      } else {
        console.log(daily.message)
      }
    },
    handleDayListUpdate() {
    this.fnDayList(this.selectedDate); 
  },
  },
  mounted() {
    this.todoauto()
    this.fnDayList()
  },
}
</script>

<style lang="scss" scoped>
.todo {
  &-wrap {
    display: flex;
    height: 825px;
    gap: 0 40px;
  }
  &-box {
    overflow: hidden;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3);
    &:nth-child(1) {
      width: calc(40% - 20px);
    }
    &:nth-child(2) {
      width: calc(60% - 20px);
      display: flex;
      gap: 0 20px;
    }
    .first {
      width: calc(40% - 10px);
    }
    .second {
      width: calc(60% - 10px);
    }
  }
}
</style>
