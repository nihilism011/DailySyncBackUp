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
      dailyList: {
      type: Object,
    },
      fnScheduleList: {
      type: Function,
    },
    fnDayList: {
      type: Function,
    },

  },

  watch: {
    // dailyList가 업데이트되면, 객체 형태에 맞게 events를 설정
    dailyList(newValue) {
      if (newValue && typeof newValue === 'object') {
        // 객체에서 필요한 데이터만 뽑아서 배열로 변환
        this.calendarOptions.events = Object.values(newValue); // 객체의 값을 배열로 변환
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
  