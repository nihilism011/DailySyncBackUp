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
      //type: Object,
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
        this.calendarOptions.events = newValue; // dailyList 데이터를 FullCalendar에 맞게 전달
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
        this.$emit('fnScheduleList', date); // 클릭된 날짜에 맞는 일정을 가져오도록 부모에 전달
      },
  
    },
    mounted() {},
  }
  </script>
  