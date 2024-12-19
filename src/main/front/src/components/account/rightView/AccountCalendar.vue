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
    'refreshStore.refreshState': 'fetchCalendarViewData',
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
      const year = this.dateStore.selectedYear
      const month = this.dateStore.selectedMonth
      const date = `${year}-${month}-01`
      calendar.gotoDate(new Date(date))
    },
    updateYearAndMonth() {
      this.$emit('updateYearMonth', this.selectYear, this.selectMonth)
    },
    async fetchAccountList() {
      console.log(this.selectYear, '==========', this.selectMonth)
      const data = await this.getAccountList(this.selectYear, this.selectMonth)
      console.log(data)
    },
    async fetchCalendarViewData() {
      const url = `account/items/month/${this.selectYear}/${this.selectMonth}`
      const { data } = await this.$axios.get(url)
      this.accountEventStore.setData(data)
      const eventData = data.flatMap((item) => {
        const result = []
        if (item.plusSumAmount !== 0) {
          result.push({
            start: item.accountDate,
            title: numToWon(item.plusSumAmount),
            color: '#ff334b',
          })
        }
        if (item.minusSumAmount !== 0) {
          result.push({
            start: item.accountDate,
            title: numToWon(item.minusSumAmount),
            color: '#4f7eff',
          })
        }
        return result
      })
      this.calendarEvents = eventData
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
      this.selectDate = dateSet.format('YYYY-MM-DD')
      this.selectYear = dateSet.get('year')
      this.selectMonth = dateSet.get('month') + 1
      this.updateYearMonth(dateSet.get('year'), dateSet.get('month') + 1)
      this.fetchCalendarViewData()
    },
  },
  mounted() {
    this.fetchCalendarViewData()
    this.fetchAccountList()
  },
}
</script>
<style lang="scss" scoped></style>
