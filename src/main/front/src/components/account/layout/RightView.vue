<template>
  <div class="account-right-container">
    <button @click="changeViewCalendar('before')">{{ '<' }}</button>
    {{ `${dateStore.selectedYear}년 ${dateStore.selectedMonth}월` }}
    <button @click="changeViewCalendar('next')">{{ '>' }}</button>
    <div class="right-top-container">
      <div class="account-fixed-wrap">
        <MonthScore />
      </div>
      <div class="account-fixed-wrap">
        <MonthFixedList />
      </div>
    </div>
    <Calendar ref="calendarRef" />
  </div>
</template>
<script>
import Calendar from '../rightView/AccountCalendar.vue'
import MonthScore from '../rightView/MonthScore.vue'
import MonthFixedList from '../rightView/MonthFixedList.vue'
import { useDateStore } from '@/stores/dateStore'
export default {
  components: {
    Calendar,
    MonthScore,
    MonthFixedList,
  },
  setup() {
    const dateStore = useDateStore()
    return { dateStore }
  },
  data() {
    return {
      year: null,
      month: null,
    }
  },
  methods: {
    changeViewCalendar(np) {
      const calendar = this.$refs.calendarRef
      calendar.changeCalendarView(np)
    },
  },
  mounted() {},
}
</script>
<style lang="scss" scoped>
.account-right-container {
  display: flex;
  flex-direction: column;
  gap: 20px 0;
}
.right-top-container {
  display: flex;
  gap: 0 20px;
}
</style>
