import { defineStore } from 'pinia'
import dayjs from 'dayjs'

export const useDateStore = defineStore('dateStore', {
  state: () => ({
    selectedDate: dayjs().format('YYYY-MM-DD'),
    selectedYear: dayjs().get('year'),
    selectedMonth: dayjs().get('month'),
  }),
  actions: {
    setSelectedDate(date) {
      this.selectedDate = date
    },
    setSelectedYear(year) {
      this.selectedYear = year
    },
    setSelectedMonth(month) {
      this.selectedMonth = month
    },
  },
})
