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
        type: Array,
      //type: Object,
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
      // if (newValue && typeof newValue === 'object') {
      //   this.calendarOptions.events = Object.values(newValue); 
      // }
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
        const selectedId = info.event.id;
        this.$emit('fnSchduleDayList', selectedId);
      },

      handleMonthChange({ view }) {
        const start = view.currentStart
        const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
        this.$emit('fnScheduleList', dateSet)
      },
      handleDateClick(info) {
        const date = info.dateStr;
        console.log("여기야",this.dailyList);
        const isSchedule = this.dailyList.start
        console.log("일정있다",isSchedule)

        // if(isSchedule && Array.isArray(isSchedule) && isSchedule.length > 0){
        //   this.$emit('fnScheduleList', info.title)
        // }
        
        
        this.$emit('fnScheduleList', info.dateStr)
      },
    },
    mounted() {},
  }
  </script>
  