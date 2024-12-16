<template>
  <div class="left left-container">
    <div class="left-top">
    </div>
    <div class="left-bottom">
      <ScheduleList 
        :fullList="dailyList" 
        :day="day" 
        :selectedSchedule="selectedSchedule" 
        @fnDayList="fnScheduleList"
        />
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch @searchResult="openModal"/>
    <ScheduleCalendar 
    :dailyList="dailyList" 
    @fnScheduleList="fnScheduleList" 
    :selectedSchedule="selectedSchedule" 
    @SelectedSchedule="SelectedSchedule" 
  />
  </div>
</template>
<script>
import ScheduleCalendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleList from '@/components/schedule/ScheduleList.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
export default {
  components: {
    ScheduleList,
    ScheduleSearch,
    ScheduleCalendar,
  },
  data() {
    return {
      day : '',
      dailyList: [],
      fullList: [],
      selectedDay: this.$dayjs().format('YYYY-MM-DD'), 
      selectedSchedule: '',
      popupState: false,
      searchResults: [],
    };
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
          console.log("dailyList:", this.dailyList);  // dailyList의 내용을 확인
          if (this.dailyList.length > 0) {
            this.selectEarliestSchedule();
          } else {
            console.log('일정 데이터가 비어 있습니다.');
          }
        } else {
          console.log('해당 날짜에 일정이 없습니다.');
          this.dailyList = [];
        }
      } catch (error) {
        console.error('일정을 불러오는 중 오류가 발생했습니다:', error);
        this.dailyList = []; 
      }
    },
    selectEarliestSchedule() {
      if (this.dailyList && Array.isArray(this.dailyList)) {
    // 날짜 비교를 dayjs를 이용해 처리
    const earliestSchedule = this.dailyList.reduce((earliest, current) => {
      const earliestStart = this.$dayjs(earliest.start);
      const currentStart = this.$dayjs(current.start);

      // 날짜 비교가 제대로 되는지 체크
      if (earliestStart.isValid() && currentStart.isValid()) {
        return currentStart.isBefore(earliestStart) ? current : earliest;
      } else {
        console.error('Invalid date format detected:', earliest.start, current.start);
        return earliest;
      }
    });

    this.selectedSchedule = earliestSchedule;
  } else {
    console.log('오늘의 일정에 오류가 있다.');
  }
    },
    async SelectedSchedule(id) {
      console.log("SelectedSchedule에 id가 들어왔습니다:", id); 
      const response = await this.$axios.get(`schedule/userId/id/${id}`);
      if (response.status) {
        console.log("response.data",response.data)
        this.selectedSchedule = response.data;  
      }else {
        console.log("일정이 없습니다.");
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day);
  },
}
</script>

<style>

</style>