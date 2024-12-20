<template>
  <div>
    <FullCalendar :options="calendarOptions" ref="calendar" @updateYearMonth="updateYearMonth" />
  </div>
</template>
<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import { numToWon, getAccountList } from '@/lib/accountLib'
import { useAccountEventStore } from '@/stores/accountEventStore'
import { useAccountStore } from '@/stores/accountStore'
export default {
  components: {
    FullCalendar,
  },
  setup() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    const accountEventStore = useAccountEventStore()
    const accountStore = useAccountStore()
    return { dateStore, refreshStore, accountEventStore, accountStore }
  },
  watch: {
    calendarEvents() {
      this.calendarOptions.events = this.calendarEvents
    },
    'accountStore.calendarList': function () {
      this.calendarOptions.events = this.accountStore.calendarList
    },
  },
  data() {
    return {
      calendarOptions: {
        locale: 'ko',
        height: 550,
        plugins: [dayGridPlugin, interactionPlugin],
        showNonCurrentDates: false,
        initialDate: this.dateStore.selectedDate || this.$dayjs().format('YYYY-MM-DD'),
        initialView: 'dayGridMonth',
        headerToolbar: false,
        events: [],
        dateClick: this.handleDateClick,
        eventClick: this.handleEventClick,
        dayCellContent: (info) => {
          return parseInt(info.dayNumberText)
        },
        datesSet: this.handleDatesChange,
        footerToolbar: {
          right: 'myCustomButton',
        },
        customButtons: {
          myCustomButton: {
            text: '오늘',
            click: () => {
              this.gotoToday()
            },
          },
        },
      },
      calendarEvents: [],
      selectDate: null,
      selectYear: null,
      selectMonth: null,
    }
  },
  methods: {
    numToWon,
    getAccountList,
    updateYearMonth(newYear, newMonth) {
      this.dateStore.setSelectedYear(newYear)
      this.dateStore.setSelectedMonth(newMonth)
    },
    gotoToday() {
      const calendar = this.$refs.calendar.getApi()
      const today = new Date()
      calendar.gotoDate(today)
      this.dateStore.setSelectedDate(this.$dayjs(today).format('YYYY-MM-DD'))
    },
    changeCalendarView(np) {
      const calendar = this.$refs.calendar.getApi()
      if (np === 'next') {
        this.dateStore.moveNextMonth()
      } else {
        this.dateStore.moveBeforeMonth()
      }
      const date = `${this.dateStore.selectedYear}-${this.dateStore.selectedMonth}-01`
      calendar.gotoDate(new Date(date))
    },
    async fetchAccountList() {
      const data = await this.getAccountList(
        this.dateStore.selectedYear,
        this.dateStore.selectedMonth,
      )
      this.accountStore.setAccountList(data)
      console.log(this.accountStore.accountList)
      console.log(this.accountStore.calendarList)
    },
    handleEventClick(info) {
      this.dateStore.setSelectedDate(info.event.startStr)
    },
    handleDateClick(info) {
      this.dateStore.setSelectedDate(info.dateStr)
    },
    handleDatesChange({ view }) {
      const start = view.currentStart
      const dateSet = this.$dayjs(start)
      this.updateYearMonth(dateSet.get('year'), dateSet.get('month') + 1)
      this.dateStore.setSelectedYear(dateSet.get('year'))
      this.dateStore.setSelectedMonth(dateSet.get('month') + 1)
      this.fetchAccountList()
    },
  },
  mounted() {
    this.fetchAccountList()
  },
}
</script>
<style lang="scss" scoped></style>
