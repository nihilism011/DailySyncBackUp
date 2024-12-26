<template>
  <FullCalendar :options="calendarOptions" @SelectedSchedule="handleEventClick" />
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
    selectedSchedule: {
      type: Object,
    },
    fnScheduleList: {
      type: Function,
    },
  },
  watch: {
    dailyList(newSchedule) {
      if (newSchedule && Array.isArray(newSchedule)) {
        this.calendarOptions.events = newSchedule;
      }
    },
  },
  data() {
    return {
      calendarOptions: {
        locale: 'kr',
        height: 700,
        showNonCurrentDates: false,
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        events: [],
        eventClick: this.handleEventClick,
        datesSet: this.handleMonthChange,
        dateClick: this.handleDateClick,
        eventContent: this.renderEventContent,
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
      },
    }
  },
  methods: {
    handleEventClick(info) {
      if (this.isAdd || this.isUpdate) {
        // 일정 등록 중일 때는 일정 클릭을 막습니다.
        return;
      }
      const id = info.event.id
      this.$emit('inputSchedule', id)
    },
    handleMonthChange({ view }) {
      const start = view.currentStart
      const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
      this.$emit('fnScheduleList', dateSet)
    },
    // handleDateClick(info) {
    //   const date = info.dateStr
    //   this.$emit('fnScheduleList', date)
    // },
    renderEventContent(eventInfo) {
      return {
        html: `<div style="text-align: center;">${eventInfo.event.title}</div>`,
      }
    },
    
    
    
  },
  mounted() {},
}
</script>
