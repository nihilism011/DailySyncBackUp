import { defineStore } from 'pinia'
import dayjs from 'dayjs'

export const useDateStore = defineStore('dateStore', {
  state: () => ({
    selectedDate: dayjs().format('YYYY-MM-DD'),
  }),
  actions: {
    setSelectedDate(date) {
      this.selectedDate = date
    },
  },
})
