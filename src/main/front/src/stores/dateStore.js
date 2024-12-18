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
    moveNextMonth() {
      if (this.selectedMonth === 12) {
        this.selectedMonth = 1
        this.selectedYear += 1
      } else {
        this.selectedMonth += 1
      }
    },
    moveBeforeMonth() {
      if (this.selectedMonth === 1) {
        this.selectedMonth = 12
        this.selectedYear -= 1
      } else {
        this.selectedMonth -= 1
      }
    },
  },
})
