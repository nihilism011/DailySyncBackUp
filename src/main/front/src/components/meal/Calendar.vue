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
    fnRequest: {
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
        datesSet: this.handleDatesChange,
      },
    }
  },
  methods: {
    handleEventClick: function (info) {
      if (typeof info === 'string') {
        this.$emit('fnRequest', info)
      } else {
        this.$emit('fnRequest', info.event.startStr)
      }
    },
    handleDatesChange({ view }) {
      const start = view.currentStart
      const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}-01`
      this.handleEventClick(dateSet)
    },
  },
  mounted() {},
}
</script>
