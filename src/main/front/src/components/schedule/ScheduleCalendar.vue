<template>
    <FullCalendar :options="calendarOptions" @SelectedSchedule="handleEventClick"/>
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
        type: Array,
    },
    selectedSchedule: {
      type: Object,
    },
    fnScheduleList: {
    type: Function,
  },
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
          eventContent: this.renderEventContent, 
        },
      }
    },
    methods: {
      handleEventClick(info) {
        console.log("info입니다", info)
        const id = info.event.id;
        console.log("선택된 일정 ID:", id); 
        this.$emit('SelectedSchedule', id); 
    },

      handleMonthChange({ view }) {
        const start = view.currentStart
        const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
        this.$emit('fnScheduleList', dateSet)
      },
      handleDateClick(info) {
        const date = info.dateStr;
        this.$emit('fnScheduleList', date); 
      },
    renderEventContent(eventInfo) {
      return {
        html: `<div style="text-align: center;">${eventInfo.event.title}</div>`
      }
    }
    },
    mounted() {
    },
  }
  </script>
  