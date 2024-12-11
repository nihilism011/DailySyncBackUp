<template>
  <FullCalendar :options="calendarOptions" />
</template>
<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'

export default {
  props: {
    dailyLsit: {
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
    dailyLsit(newValue) {
      if (newValue && Array.isArray(newValue)) {
        this.calendarOptions.events = this.dailyLsit
      }
    },
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        events: [],
        eventClick: this.handleEventClick,
        datesSet: this.handleMonthChange,
        dateClick: this.handleDateClick,
      },
    }
  },
  methods: {
    handleEventClick: function (info) {
      if (typeof info === 'string') {
        this.$emit('fnMealList', info)
      } else {
        this.$emit('fnMealList', info.event.startStr)
      }
    },
    handleMonthChange({ view }) {
      const start = view.currentStart
      const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
      this.$emit('fnDayList', dateSet)
    },
    handleDateClick() {
      console.log('날짜선택')
    },
  },
  mounted() {},
}
</script>
