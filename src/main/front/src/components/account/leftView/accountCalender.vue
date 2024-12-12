<template>
  <FullCalendar :options="calendarOptions" />
</template>
<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import { useDateStore } from '@/stores/dateStore'
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
    return { dateStore }
  },
  watch: {
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
      calendarData: [],
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        initialDate: this.dateStore.selectedDate,
        headerToolbar: {
          left: 'prev',
          center: 'title',
          right: 'next',
        },
        events: [],
        dateClick: this.handleDateClick,
        dayCellContent: this.dayCellContent,
        datesSet: this.handleDatesChange,
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
      const transformedData = data.flatMap((item) => {
        const result = []
        if (item.plusSumAmount !== 0) {
          result.push({
            start: item.accountDate,
            title: '+' + numToWon(item.plusSumAmount),
            color: 'red',
            className: 'asdf',
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
<style lang="scss" scoped></style>
