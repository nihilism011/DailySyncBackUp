<template>
  <FullCalendar :options="calendarOptions" />
</template>
<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import { useDateStore } from '@/stores/dateStore'

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
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        initialDate: this.dateStore.selectedDate,
        headerToolbar: {
          left: 'prev',
          center: 'title',
          right: 'next',
        },
        events: [
          {
            title: '임시',
            start: '2024-12-04',
          },
        ],
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
