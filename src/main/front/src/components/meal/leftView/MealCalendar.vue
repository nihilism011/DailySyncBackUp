<template>
  <FullCalendar :options="calendarOptions" />
</template>
<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'

export default {
  props: {
    dailyList: {
      type: Object,
    },
    fnMealList: {
      type: Function,
    },
    fnDayList: {
      type: Function,
    },
  },
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  watch: {
    dailyList(newValue) {
      if (newValue && Array.isArray(newValue)) {
        this.calendarOptions.events = this.dailyList
      }
    },
  },
  data() {
    return {
      calendarOptions: {
        locale: 'ko',
        height: 560,
        showNonCurrentDates: false,
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        events: [],
        eventClick: this.handleEventClick,
        datesSet: this.handleMonthChange,
        dateClick: this.handleDateClick,
        headerToolbar: {
          left: 'prev',
          center: 'title',
          right: 'next',
        },
        footerToolbar: {
          right: 'today',
        },
        buttonText: {
          today: '오늘',
        },
        dayCellContent: function (info) {
          return parseInt(info.dayNumberText)
        },
      },
    }
  },
  methods: {
    handleEventClick: function (info) {
      this.$emit('fnMealList', info.event.startStr)
    },
    handleMonthChange({ view }) {
      const start = view.currentStart
      const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
      this.$emit('fnDayList', dateSet)
    },
    handleDateClick(info) {
      this.$emit('fnMealList', info.dateStr)
    },
  },
  mounted() {},
}
</script>
