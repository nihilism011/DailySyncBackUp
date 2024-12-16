<template>
  <FullCalendar :options="calendarOptions" ref="calendar" />
  <div class="list-item">
    <div class="tit-box">
      <div class="title">
        {{
          `${$dayjs(dateStore.selectedDate).get('year')}년 ${$dayjs(dateStore.selectedDate).get('month') + 1}월`
        }}
      </div>
      <div class="month-sum">
        <div>수입 {{ totalPlusSum }}</div>
        <div>지출 {{ totalMinusSum }}</div>
      </div>
    </div>
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
    'refreshStore.refreshState': function () {
      this.fetchData(this.dateStore.selectedDate)
    },
    'dateStore.selectedDate': function (newDate) {
      this.handleDate(new Date(newDate))
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
        initialDate: this.dateStore.selectedDate || this.$dayjs().format('YYYY-MM-DD'),
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
          right: 'myCustomButton',
        },
        customButtons: {
          myCustomButton: {
            text: 'today',
            click: () => {
              this.gotoToday()
            },
          },
        },
      },
    }
  },
  methods: {
    gotoToday() {
      this.dateStore.setSelectedDate(this.$dayjs().format('YYYY-MM-DD'))
    },
    handleDate(date) {
      this.fetchData()
      const calendar = this.$refs.calendar.getApi()
      calendar.gotoDate(date)
    },
    numToWon,
    async fetchData(date) {
      console.log(date)
      const year = this.$dayjs(date).get('year')
      const month = this.$dayjs(date).get('month') + 1
      const url = `account/items/month/${year}/${month}`
      console.log(url)
      const { data } = await this.$axios.get(url)
      let plus = 0
      let minus = 0
      data.map((item) => {
        plus += item.plusSumAmount
        minus += item.minusSumAmount
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
      this.dateStore.setSelectedDate(info.event.startStr)
    },
    handleDateClick(info) {
      this.dateStore.setSelectedDate(info.dateStr)
    },
    handleDatesChange({ view }) {
      const start = view.currentStart
      const dateSet = this.$dayjs(start).format('YYYY-MM-DD')
      this.fetchData(dateSet)
    },
  },
  mounted() {
    this.fetchData(this.dateStore.selectedDate)
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
