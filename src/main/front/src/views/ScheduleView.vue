<template>
  <div class="left left-container">
    <div class="left-top"></div>
    <div class="left-bottom">
      <NewInput 
        :dailyList="dailyList" 
        :day="day" 
        :inputSchedule="inputSchedule" 
        @fnDayList="fnScheduleList" 
        @inputedSchedule="inputedSchedule"
        />
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch @searchResult="openModal"/>
    <ScheduleCalendar 
    :dailyList="dailyList" 
    :inputSchedule="inputSchedule" 
    @fnScheduleList="fnScheduleList" 
    @inputSchedule="inputedSchedule" 
  />
   <!-- 모달을 표시 -->
   <ScheduleModal
    v-if="popupState"
    :popupState="popupState"
    :searchResults="searchResults"
    @closePopup="closeModal"
  />
  </div>
</template>
<script>
import NewInput from '@/components/schedule/NewInput.vue';
import ScheduleCalendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
import ScheduleModal from '@/components/schedule/ScheduleModal.vue';
export default {
  components: {
    NewInput,
    ScheduleSearch,
    ScheduleCalendar,
    ScheduleModal
  },
  data() {
    return {
      day: '',
      dailyList: [],
      fullList: [],
      inputSchedule: {
        title: '',
        description: '',
        startTime: '',  
        endTime: '',    
      },
      popupState: false,
      searchResults: [],
    }
  },
  methods: {
    // 모달을 여는 함수
    openModal(results) {
      this.searchResults = results;  // 검색 결과를 설정
      this.popupState = true;        // 모달을 열기
    },
    // 모달을 닫는 함수
    closeModal() {
      this.popupState = false; // 모달 닫기
    },
    async fnScheduleList(inputDay) {
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];

      const startOfMonth = `${year}-${month}-01T00:00:00`; // 해당 월의 첫 날 (00:00:00)
      const endOfMonth = `${year}-${month}-01T23:59:59`;   // 해당 월의 마지막 날 (23:59:59)
      try {
      // 서버로 GET 요청 보내기
      const full = await this.$axios.get(`schedule/userId/scheduleList/${year}/${month}`, {
        params: { startOfMonth, endOfMonth } // startOfMonth와 endOfMonth를 쿼리 파라미터로 전달
      });

        if (full.status && full.data.length > 0) {
          this.dailyList = full.data.map(item => ({
            id: item.id,
            title: item.title,
            start: item.startTime,
            end: item.endTime,
            description: item.description,
          }));
          console.log("Server response: ", full.data);
        } else {
          console.log('해당 날짜에 일정이 없습니다.');
          this.dailyList = [];
        }
      } catch (error) {
        console.error('일정을 불러오는 중 오류가 발생했습니다:', error);
        this.dailyList = []; 
      }
    },
    async inputedSchedule(id) {
      const response = await this.$axios.get(`schedule/userId/id/${id}`);
      if (response.status) {
        console.log("response.data",response.data)
        this.inputSchedule = response.data;  
      }else {
        console.log("일정이 없습니다.");
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day)
  },
}
</script>

<style></style>