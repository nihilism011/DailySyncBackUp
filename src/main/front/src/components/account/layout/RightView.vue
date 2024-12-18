<template>
  <div class="account-right-container">
    <div class="fc none">
      <div class="fc-toolbar">
        <div class="fc-toolbar-chunk">
          <div class="fc-button">
            <button
              @click="changeViewCalendar('before')"
              class="fc-icon fc-icon-chevron-left"
            ></button>
          </div>
        </div>
        <div class="fc-toolbar-chunk">
          <h2 class="fc-toolbar-title">
            {{ `${dateStore.selectedYear}년 ${dateStore.selectedMonth}월` }}
          </h2>
        </div>
        <div class="fc-toolbar-chunk">
          <div class="fc-button">
            <button
              @click="changeViewCalendar('next')"
              class="fc-icon fc-icon-chevron-right"
            ></button>
          </div>
        </div>
      </div>
    </div>
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
