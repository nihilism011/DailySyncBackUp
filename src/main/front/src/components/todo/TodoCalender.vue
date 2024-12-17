<template>
  <!-- FullCalendar 컴포넌트 -->
  <FullCalendar :options="calendarOptions" />

  <Modal
      :isVisible="isModalVisible"
      :selectedDate="selectedDate" 
      @close="closeModal"
  />
</template>

<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import Modal from './TodoSelectModal.vue'

export default {
  props : {

    dailyList: {
    type: Object,
     },
  },
  components: {
    FullCalendar,
    Modal
  },
  fnDayList: {
    type: Function,
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
      selectedDate: null, // 선택된 날짜
      isModalVisible: false, // 모달의 표시 여부
      calendarOptions: {
        locale: 'ko', // 한국어 설정
        height: 540,
        showNonCurrentDates: false,
        plugins: [dayGridPlugin, interactionPlugin], // 플러그인 등록
        initialView: 'dayGridMonth', // 월별 뷰 설정
        events: [], // 이벤트 초기화
        eventClick: this.handleEventClick, // 일정 클릭 시 호출
        datesSet: this.handleMonthChange,
        dateClick: this.handleDateClick, // 날짜 클릭 시 호출
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
        dayCellContent: function (info) {
          return parseInt(info.dayNumberText)
        },
      },
    };
  },
  methods: {
    openAddItemModal() {
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
    },
    handleDateClick(info) {
      this.selectedDate = info.dateStr; // 선택된 날짜 저장
      this.openAddItemModal(); // 모달 열기
    },
    handleEventClick(info) {
      const eventStartDate = info.event.start;
      eventStartDate.setDate(eventStartDate.getDate() + 1);
      const eventDateStr = eventStartDate.toISOString().split('T')[0];
      this.selectedDate = eventDateStr;
      this.openAddItemModal(); // 모달 열기
    },
    handleMonthChange({ view }) {
      const start = view.currentStart
      const dateSet = `${start.getFullYear()}-${start.getMonth() + 1}`
      this.$emit('fnDayList', dateSet)
    },
  },
}
</script>
