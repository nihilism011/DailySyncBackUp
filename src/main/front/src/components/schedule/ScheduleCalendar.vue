<template>
    <FullCalendar :options="calendarOptions" />
  </template>
  <script>
  import FullCalendar from '@fullcalendar/vue3'
  import dayGridPlugin from '@fullcalendar/daygrid'
  import interactionPlugin from '@fullcalendar/interaction'
  
  export default {
    components: {
      FullCalendar, 
    },

    props: {
      dailyLsit: {
      type: Object,
    },
      fnScheduleList: {
      type: Object,
    },
    fnDayList: {
      type: Function,
    },

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
          locale: 'kr',
          height: 500,
          showNonCurrentDates: false,
          handleWindowResize: false,
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
          this.$emit('fnScheduleList', info)
        } else {
          this.$emit('fnScheduleList', info.event.startStr)
        }
      },
      handleMonthChange({ view }) {
        const start = view.currentStart
        const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
        this.$emit('fnDayList', dateSet)
      },
      handleDateClick(info) {
        this.$emit('fnScheduleList', info.dateStr)
      },
    },
    mounted() {},
  }
  </script>
  