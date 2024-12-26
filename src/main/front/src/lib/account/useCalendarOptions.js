import { ref, reactive } from 'vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'

export const useCalendarOptions = (dateStore, accountStore, calendar) => {
  const handleEventClick = (info) => {
    dateStore.setSelectedDate(info.event.startStr)
  }

  const handleDateClick = (info) => {
    dateStore.setSelectedDate(info.dateStr)
  }

  const handleDatesChange = async ({ view }) => {
    const start = view.currentStart
    const dateSet = new Date(start)
    updateYearMonth(dateSet.getFullYear(), dateSet.getMonth() + 1)
    dateStore.setCalendarYear(dateSet.getFullYear())
    dateStore.setCalendarMonth(dateSet.getMonth() + 1)
    await accountStore.initAccountList(dateSet.getFullYear(), dateSet.getMonth() + 1)
  }

  const updateYearMonth = (newYear, newMonth) => {
    dateStore.setCalendarYear(newYear)
    dateStore.setCalendarMonth(newMonth)
  }

  const gotoToday = () => {
    const today = new Date()
    calendar.value?.getApi().gotoDate(today)
    dateStore.setSelectedDate(today.toISOString().split('T')[0])
  }

  return reactive({
    locale: 'ko',
    height: 550,
    plugins: [dayGridPlugin, interactionPlugin],
    showNonCurrentDates: false,
    initialDate: dateStore.selectedDate || new Date().toISOString().split('T')[0], // Default to today's date if no selectedDate
    initialView: 'dayGridMonth',
    headerToolbar: null,
    events: [],
    dateClick: handleDateClick,
    eventClick: handleEventClick,
    dayCellContent: (info) => parseInt(info.dayNumberText),
    datesSet: handleDatesChange,
    footerToolbar: {
      right: 'myCustomButton',
    },
    customButtons: {
      myCustomButton: {
        text: '오늘',
        click: gotoToday,
      },
    },
  })
}
