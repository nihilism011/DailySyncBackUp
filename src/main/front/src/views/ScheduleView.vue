<template>
  <div class="left left-container">
    <div class="left-top"></div>
    <div class="left-bottom">
      <NewInput 
        :fullList="dailyList" 
        :day="day" 
        :inputSchedule="inputSchedule" 
        @fnDayList="fnScheduleList"
        @updateInputSchedule="updateInputSchedule"
        />
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch @searchResult="openModal"/>
    <ScheduleCalendar 
    :dailyList="dailyList" 
    @fnScheduleList="fnScheduleList" 
    :inputSchedule="inputSchedule" 
    @inputSchedule="inputedSchedule" 
  />
  </div>
</template>
<script>
import NewInput from '@/components/schedule/NewInput.vue';
import ScheduleCalendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
export default {
  components: {
    NewInput,
    ScheduleSearch,
    ScheduleCalendar,
  },
  data() {
    return {
      day: '',
      dailyList: [],
      fullList: [],
      inputSchedule: {
      title: '',
      description: '',
      startTime: '',  // 빈 값으로 초기화
      endTime: '',    // 빈 값으로 초기화
    },
      popupState: false,
      searchResults: [],
    }
  },

  methods: {
    async fnScheduleList(inputDay) {
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];
      try {
        const full = await this.$axios.get(`schedule/userId/${year}/${month}`);
        console.log("API 응답:", full);

        if (full.status && full.data.length > 0) {
          this.dailyList = full.data.map(item => ({
            id: item.id,
            title: item.title,
            start: item.startTime,
            end: item.endTime,
            description: item.description,
          }));
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
      console.log("inputedSchedule에 id가 들어왔습니다:", id); 
      const response = await this.$axios.get(`schedule/userId/id/${id}`);
      if (response.status) {
        console.log("response.data",response.data)
        this.inputSchedule = response.data;  
      }else {
        console.log("일정이 없습니다.");
      }
    },
    // 부모에서 자식의 'inputSchedule' 변경을 처리하는 메서드
    updateInputSchedule(newSchedule) {
      this.inputSchedule = newSchedule;
    }
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day)
  },
}
</script>

<style></style>