<script setup>
import { ref } from 'vue'
import Calendar from '../rightView/AccountCalendar.vue'
import MonthScore from '../rightView/MonthScore.vue'
import MonthFixedList from '../rightView/MonthFixedList.vue'
import { useDateStore } from '@/stores/account/dateStore'

const dateStore = useDateStore()
const calendarRef = ref(null)

const changeViewCalendar = (np) => {
  const calendar = calendarRef.value
  if (calendar) {
    calendar.changeCalendarView(np)
  }
}
</script>
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
            {{ `${dateStore.calendarYear}년 ${dateStore.calendarMonth}월` }}
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
