<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import { useDateStore } from '@/stores/account/dateStore'
import { useAccountStore } from '@/stores/account/accountStore'
import { useCalendarOptions } from '@/lib/account/useCalendarOptions'

const dateStore = useDateStore()
const accountStore = useAccountStore()

const calendar = ref(null)
const calendarOptions = useCalendarOptions(dateStore, accountStore, calendar)
const changeCalendarView = (np: 'next' | 'before') => {
  if (!calendar.value) return

  const calendarApi = calendar.value.getApi()
  if (np === 'next') {
    dateStore.moveNextMonth()
  } else {
    dateStore.moveBeforeMonth()
  }

  const date = `${dateStore.calendarYear}-${dateStore.calendarMonth}-01`
  calendarApi.gotoDate(new Date(date))
}
watch(
  () => accountStore.calendarList,
  (list) => {
    calendarOptions.events = list
  },
)
defineExpose({
  changeCalendarView,
})
</script>

<template>
  <div>
    <FullCalendar :options="calendarOptions" ref="calendar" />
  </div>
</template>
