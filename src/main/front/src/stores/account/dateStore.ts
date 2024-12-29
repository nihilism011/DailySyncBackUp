import { defineStore } from 'pinia'
import dayjs from 'dayjs'

interface DateState {
  selectedDate: string
  calendarYear: number
  calendarMonth: number
}

export const useDateStore = defineStore('dateStore', {
  state: (): DateState => ({
    selectedDate: dayjs().format('YYYY-MM-DD'),
    calendarYear: dayjs().get('year'),
    calendarMonth: dayjs().get('month') + 1,
  }),
  actions: {
    setSelectedDate(date: string): void {
      this.selectedDate = date
    },

    setCalendarYear(year: number): void {
      this.calendarYear = year
    },

    setCalendarMonth(month: number): void {
      this.calendarMonth = month
    },

    moveNextMonth(): void {
      if (this.calendarMonth === 12) {
        this.calendarMonth = 1
        this.calendarYear += 1
      } else {
        this.calendarMonth += 1
      }
    },
    moveBeforeMonth(): void {
      if (this.calendarMonth === 1) {
        this.calendarMonth = 12
        this.calendarYear -= 1
      } else {
        this.calendarMonth -= 1
      }
    },
  },
})
