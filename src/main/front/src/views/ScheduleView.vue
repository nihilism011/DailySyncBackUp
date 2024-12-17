<template>
  <div class="left left-container">
    <div class="left-top"></div>
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
      day: '',
      dailyList: [],
      fullList: [],
      selectedDay: this.$dayjs().format('YYYY-MM-DD'),
      selectedSchedule: '',
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
          this.selectEarliestSchedule(inputDay); 
      } else {
        console.log('해당 날짜에 일정이 없습니다.');
      }
    } catch (error) {
      console.error('일정을 불러오는 중 오류가 발생했습니다:', error);
    }
    },
    selectEarliestSchedule(todayDate) {
  if (this.dailyList.length === 0) {
    this.selectedSchedule = null;
    return;
  }

  const formattedTodayDate = this.$dayjs().format('YYYY-MM-DD');
  console.log("오늘 날짜 (formattedTodayDate):", formattedTodayDate);  

  // 오늘 날짜의 시작과 끝 시간
  const startOfToday = this.$dayjs(formattedTodayDate).startOf('day');
  const endOfToday = this.$dayjs(formattedTodayDate).endOf('day');

  // 오늘 일정들 필터링
  const todaySchedules = this.dailyList.filter(item => {
    const scheduleStart = this.$dayjs(item.start);  // 일정 시작 시간
    const scheduleEnd = this.$dayjs(item.end);      // 일정 끝 시간

    // 일정이 오늘 날짜 범위와 겹치는지 확인
    return (
      (scheduleStart.isBefore(endOfToday) && scheduleEnd.isAfter(startOfToday)) // 일정이 오늘 범위와 겹치는 경우
    );
  });

  console.log("오늘 일정들 (todaySchedules):", todaySchedules);

  if (todaySchedules.length === 0) {
    console.log("오늘 일정이 없습니다.");
    return;
  }

  // 가장 빠른 일정 찾기
  const earliestSchedule = todaySchedules.reduce((earliest, current) => {
    const currentStart = new Date(current.start);  
    const earliestStart = new Date(earliest.start);
    return currentStart < earliestStart ? current : earliest;
  });

  this.selectedSchedule = {
    ...earliestSchedule,
    startTime: this.$dayjs(earliestSchedule.start).format('YYYY-MM-DDTHH:mm'), 
    endTime: this.$dayjs(earliestSchedule.end).format('YYYY-MM-DDTHH:mm') 
  };
  
  console.log("오늘 가장 빠른 일정:", this.selectedSchedule);  
},
 
    async SelectedSchedule(id){
      const userId = 6;
      console.log("SelectedSchedule에 id가 들어왔습니다:", id); 
      const response = await this.$axios.get(`schedule/userId/id/${userId}/${id}`);
        if (response.status) {
          this.selectedSchedule = response.data[0];  
      } else {
        console.log("일정이 없습니다.");
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
    this.fnScheduleList(this.day)
  },
}
</script>

<style></style>
