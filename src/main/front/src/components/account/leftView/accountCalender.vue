<template>
  <FullCalendar :options="calendarOptions" />
  <div>{{ $dayjs(dateStore.selectedDate).get('month') + 1 }} 월 총합</div>
  <div class="month-sum">
    <div>수입 : {{ totalPlusSum }}</div>
    <div>지출 : {{ totalMinusSum }}</div>
  </div>
</template>
<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import { numToWon } from '@/lib/accountLib'
export default {
  components: {
    FullCalendar,
  },
  props: {
    fnRequest: {
      type: Function,
    },
  },
  setup() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    return { dateStore, refreshStore }
  },
  watch: {
    'refreshStore.refreshState': 'fetchData',
    'dateStore.selectedDate': function (newDate) {
      this.updateCalendarDate(newDate)
      this.fetchData()
    },
    calendarData() {
      this.calendarOptions.events = this.calendarData
    },
  },
  data() {
    return {
      totalPlusSum: 0,
      totalMinusSum: 0,
      calendarData: [],
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        showNonCurrentDates: false,
        initialView: 'dayGridMonth',
        headerToolbar: {
          left: 'prev',
          center: 'title',
          right: 'next',
        },
        events: [],
        dateClick: this.handleDateClick,
        eventClick: this.handleEventClick,
        dayCellContent: this.dayCellContent,
        datesSet: this.handleDatesChange,
        height: '100%',
        footerToolbar: {
          right: 'today',
        },
      },
    }
  },
  methods: {
    numToWon,
    async fetchData() {
      const year = this.$dayjs(this.dateStore.selectedDate).get('year')
      const month = this.$dayjs(this.dateStore.selectedDate).get('month') + 1
      const url = `account/items/month/${year}/${month}`
      const { data } = await this.$axios.get(url)
      let plus = 0
      let minus = 0
      data.map((item) => {
        if (
          this.$dayjs(item.accountDate).get('month') ===
          this.$dayjs(this.dateStore.selectedDate).get('month')
        ) {
          plus += item.plusSumAmount
          minus += item.minusSumAmount
        }
      })
      this.totalPlusSum = this.numToWon(plus)
      this.totalMinusSum = this.numToWon(minus)
      const transformedData = data.flatMap((item) => {
        const result = []
        if (item.plusSumAmount !== 0) {
          result.push({
            start: item.accountDate,
            title: '+' + numToWon(item.plusSumAmount),
            color: 'red',
            classNames: ['asdf'],
          })
        }
        if (item.minusSumAmount !== 0) {
          result.push({
            start: item.accountDate,
            title: '-' + numToWon(item.minusSumAmount),
            color: 'blue',
          })
        }
        return result
      })
      this.calendarData = transformedData
    },
    handleEventClick(info) {
      const dateSet = info.event.startStr
      this.handleDateClick({ dateStr: dateSet })
    },
    handleDateClick(info) {
      this.dateStore.setSelectedDate(info.dateStr)
    },
    handleDatesChange({ view }) {
      const start = view.currentStart
      const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}-01`
      this.handleDateClick({ dateStr: dateSet })
    },
    updateCalendarDate(newDate) {
      if (this.calendar) {
        this.calendar.gotoDate(newDate)
      }
    },
  },
  mounted() {
    this.fetchData()
  },
}
</script>
<style lang="scss" scoped>
.month-sum {
  display: flex;
}
.asdf {
  font-size: 50px;
}
</style>
